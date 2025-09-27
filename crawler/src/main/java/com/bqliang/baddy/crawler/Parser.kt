package com.bqliang.baddy.crawler

import com.bqliang.baddyrank.core.network.data.PlayerDto
import com.bqliang.baddyrank.core.network.data.RankingDto
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

typealias Tr = Element

suspend fun HttpResponse.bodyAsDocument(): Document = Jsoup.parse(bodyAsText())

suspend fun Document.parseRankTable() : List<RankingDto> = withContext(Dispatchers.Default) {
    // 使用 CSS 选择器 "table.ranktables" 定位到包含排名数据的 <table> 元素
    val rankTable = selectFirst("table.ranktables") ?: error("Could not find rank table")

    // 从表格的 <tbody> 中选择所有的 <tr> (行) 元素, 忽略第一行表头
    val trs = rankTable.select("tbody > tr").drop(1)

    val rankings = trs.map { tr ->
        tr.parseRanking()
    }

    return@withContext rankings
}

private suspend fun Tr.parseRanking(): RankingDto = withContext(Dispatchers.Default) {
    // 在当前行中，获取所有的 <td> (单元格) 元素
    val cells = select("td")
    val includeTournaments = cells.size >= 6 // 是否带有 "站数" 列

    // [0] 排名
    val rankCell = cells[0]
    val rank = rankCell.text().filter { it.isDigit() }.toIntOrNull() ?: 0
    val hasProtectedRanking = rankCell.selectFirst("img[title*='排名保护']") != null

    // [3] 排名趋势
    val trendCell = cells[3]
    val positions = trendCell.text().filter { it.isDigit() }.toIntOrNull() ?: 0
    val cellHtml = trendCell.html()
    val rankChange = when {
        "arrow-down" in cellHtml -> -positions // 如果 HTML 包含 "arrow-down"，则为负数
        "arrow-up" in cellHtml -> positions  // 如果包含 "arrow-up"，则为正数
        else -> 0          // 其他情况（如"-"）为 0
    }

    // 站数
    val tournaments = if (includeTournaments) cells[4].text().toIntOrNull() else null
    // 积分
    val points = cells[5 - if (includeTournaments) 0 else 1].text().toIntOrNull() ?: 0

    val countryElements = cells[1].select("div.country") // 选取所有国籍 div
    val playerElements = cells[2].select("div.player")   // 选取所有球员 div

    val players = playerElements.mapIndexed { index, playerElement ->
        val countryElement = countryElements.getOrNull(index)

        // 从对应的元素中解析资料
        val countryAbbreviation = countryElement?.selectFirst("span")?.text() ?: "N/A"
        val countryFlagUrl = countryElement?.selectFirst("img")?.attr("data-src")
            ?.let { BASE_URL.dropLast(1) + it }.orEmpty()

        val playerLink = playerElement.selectFirst("a")
        val id = playerLink?.attr("href")?.substringAfter("sid=") ?: "N/A"
        val avatarUrl = playerLink?.selectFirst("img.face")?.attr("data-src")
            ?.let { BASE_URL.dropLast(1) + it } ?: "N/A"
        val chineseName = playerLink?.selectFirst("span")?.ownText()?.trim() ?: "N/A"
        val englishName = playerLink?.selectFirst("span.graytext")?.text() ?: "N/A"

        PlayerDto(
            id = id,
            countryAbbreviation = countryAbbreviation,
            countryFlagUrl = countryFlagUrl,
            avatarUrl = avatarUrl,
            chineseName = chineseName,
            englishName = englishName
        )
    }

    RankingDto(
        rank = rank,
        protectedRank = hasProtectedRanking,
        players = players,
        rankTrend = rankChange,
        tournaments = tournaments,
        points = points
    )
}

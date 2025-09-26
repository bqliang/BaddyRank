package com.bqliang.baddy.crawler

import com.bqliang.baddyrank.core.network.data.PlayerDto
import com.bqliang.baddyrank.core.network.data.RankingDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.File

const val BASE_URL = "https://www.badmintoncn.com/"

val json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
}

fun main(): Unit = runBlocking {
    val client = HttpClient(OkHttp) {

        defaultRequest {
            url(BASE_URL)
        }

        install(HttpCookies)

//        install(HttpTimeout) {
//            requestTimeoutMillis = 1000
//        }

        install(Logging) {
            level = LogLevel.ALL
        }
    }

    RankingType.entries.forEach { rankingType ->
        MatchType.entries.forEach { matchType ->
            launch(Dispatchers.Default) {
                val document = client.getDocument("${rankingType.code}.php?type=${matchType.code}")

                if ("数据有误" in document.text()) {
                    cancel("Invalid ranking data")
                }

                // 使用 CSS 选择器 "table.ranktables" 定位到包含排名数据的 <table> 元素
                val rankTable =
                    document.selectFirst("table.ranktables") ?: error("Could not find rank table")

                // 从表格的 <tbody> 中选择所有的 <tr> (行) 元素, 忽略第一行，因为第一行是表头
                val playerRows = rankTable.select("tbody > tr").drop(1)

                val rankings =
                    playerRows.map { row ->
                        row.parseRanking()
                    }

                val jsonString = json.encodeToString(rankings)
                launch(Dispatchers.IO) {
                    val dir = File("result" + File.separator + rankingType.lowercaseName)
                    dir.mkdirs()
                    val file = File(dir, "${matchType.lowercaseName}.json")
                    file.createNewFile()
                    file.writeText(jsonString)
                }
            }

        }
    }
}


suspend fun HttpClient.getDocument(url: String): Document {
    val httpResponse = get(url)
    var doc = Jsoup.parse(httpResponse.bodyAsText())
    if (httpResponse.status != HttpStatusCode.OK && "验证" in doc.title()) {
        requestVerification(doc)
        val textBody = get(url).bodyAsText()
        doc = Jsoup.parse(textBody)
    }

    return doc
}


suspend fun HttpClient.requestVerification(doc: Document) {
    val questionElement =
        doc.selectFirst("td.bigtext:contains(问题：)") ?: error("Could not find question element")
    val akElement = doc.selectFirst("input[name=ak]") ?: error("Could not find ak element")
    val submitButtonElement =
        doc.selectFirst("input[type=submit]") ?: error("Could not find submit button element")

    val question = questionElement.text()
    val ak = akElement.`val`()
    val submitButtonValue = submitButtonElement.`val`()

    val answer = DeepSeekApi.getAnswer(question).getOrThrow()

    submitForm(
        url = "cbo_function.php?action=clickcookie",
        formParameters = Parameters.build {
            append("ak", ak)
            append("a", answer)
            append("submit", submitButtonValue)
            append("referer", "")
        },
    ) {
        header(HttpHeaders.Referrer, BASE_URL + url)
    }
}

typealias Row = Element

suspend fun Row.parseRanking(): RankingDto = withContext(Dispatchers.Default) {
    runCatching {
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
    }.getOrThrow()
}
package com.bqliang.baddy.crawler

import com.bqliang.baddy.crawler.captcha.CaptchaDataCache
import com.bqliang.baddy.crawler.captcha.CaptchaRequiredException
import com.bqliang.baddy.crawler.captcha.CaptchaSolver
import com.bqliang.baddy.crawler.captcha.InvalidDataException
import com.bqliang.baddy.crawler.model.Discipline
import com.bqliang.baddy.crawler.model.RankingCategory
import com.bqliang.baddyrank.core.network.data.RankingDto
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.http.HttpHeaders
import io.ktor.http.Parameters
import io.ktor.http.path
import kotlinx.coroutines.CancellationException
import org.jsoup.nodes.Document

private val logger = KotlinLogging.logger {  }


class CrawlerService(
    private val httpClient: HttpClient,
    private val captchaSolver: CaptchaSolver,
) {
    suspend fun fetchRanking(
        rankingCategory: RankingCategory,
        discipline: Discipline,
        rw: String? = "",
    ): List<RankingDto> {
        val document = getRankingPage(rankingCategory, discipline, rw)

        if ("验证" in document.title()) {
            throw CaptchaRequiredException()
        }

        if ("数据有误" in document.text()) {
            throw InvalidDataException()
        }

        return document.parseRankTable()
    }


    /**
     * 确保 cbo_click_cookie 存在，如果不存在，则通过验证码获取。
     */
    suspend fun ensureSessionIsValid() {
        if (httpClient.hasCboClickCookie()) {
            logger.info { "Cookie check passed." }
            return
        }

        logger.info { "Cookie not found. Attempting to solve captcha..." }
        val doc = httpClient.get("ranking.php").bodyAsDocument()
        if ("验证" !in doc.title()) {
            // 如果没有出现验证页面，可能网站逻辑有变，或者我们已经有权限了
            if (httpClient.hasCboClickCookie()) return // 再次检查
            error("Could not find captcha page, but cookie is still missing.")
        }

        val questionElement = doc.selectFirst("td.bigtext:contains(问题：)") ?: error("Could not find question element")
        val akElement = doc.selectFirst("input[name=ak]") ?: error("Could not find ak element")
        val submitButtonElement = doc.selectFirst("input[type=submit]") ?: error("Could not find submit button element")

        val question = questionElement.text()
        val ak = akElement.`val`()
        val submitButtonValue = submitButtonElement.`val`()

        logger.info { "Solving captcha question: $question" }
        val answer = captchaSolver.solveTextCaptcha(question)
        logger.info { "AI solver: $question -> $answer" }

        httpClient.submitForm(
            formParameters = Parameters.build {
                append("ak", ak)
                append("a", answer)
                append("submit", submitButtonValue)
                append("referer", "")
            },
        ) {
            url {
                path("cbo_function.php")
                parameter("action", "clickcookie")
            }
            header(HttpHeaders.Referrer, BASE_URL + "ranking.php")
        }

        val cacheCaptchaSolver: CaptchaDataCache? = captchaSolver as? CaptchaDataCache

        if (!httpClient.hasCboClickCookie()) {
            cacheCaptchaSolver?.removeCacheCaptchaData(question)
            error("Failed to get cbo_click_cookie after submitting captcha.")
        }

        cacheCaptchaSolver?.saveCacheCaptchaData(question, answer)

        logger.info { "Successfully obtained cookie." }
    }

    private suspend fun getRankingPage(
        rankingCategory: RankingCategory,
        discipline: Discipline,
        rw: String? = null,
    ): Document {
        return httpClient.get {
            url {
                path("${rankingCategory.path}.php")
                parameter("type", discipline.code)
                if (!rw.isNullOrEmpty()) {
                    parameter("rw", rw)
                }
            }
        }.bodyAsDocument()
    }
}
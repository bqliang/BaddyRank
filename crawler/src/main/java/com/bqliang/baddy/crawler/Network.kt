package com.bqliang.baddy.crawler

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.cookies.cookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import kotlinx.serialization.json.Json

const val BASE_URL = "https://www.badmintoncn.com/"

fun createHttpClient(): HttpClient = HttpClient(OkHttp) {
    defaultRequest {
        url(BASE_URL)
    }

    install(HttpCookies)

    install(HttpTimeout) {
        requestTimeoutMillis = 1000 * 10
        connectTimeoutMillis = 1000 * 10
        socketTimeoutMillis = 1000 * 10
    }

    install(Logging) {
        level = LogLevel.ALL
    }
}

suspend fun HttpClient.hasCboClickCookie(): Boolean {
    val cookies = this.cookies(BASE_URL)
    return cookies.any { it.name.startsWith("cbo_click_cookie") }
}

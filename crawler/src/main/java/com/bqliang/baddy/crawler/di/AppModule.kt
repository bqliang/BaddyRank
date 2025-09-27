package com.bqliang.baddy.crawler.di

import com.aallam.openai.client.OpenAI
import com.bqliang.baddy.crawler.CrawlerService
import com.bqliang.baddy.crawler.captcha.AICaptchaSolver
import com.bqliang.baddy.crawler.captcha.CaptchaSolver
import com.bqliang.baddy.crawler.createHttpClient
import com.bqliang.baddy.crawler.createJson
import com.bqliang.baddy.crawler.createOpenAIClient
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val appModule = module {
    single<HttpClient> { createHttpClient() }
    single<OpenAI> { createOpenAIClient() }
    single<CaptchaSolver> { AICaptchaSolver(openAIClient = get()) }
    single<Json> { createJson() }
    single<CrawlerService> { CrawlerService(httpClient = get(), captchaSolver = get()) }
}

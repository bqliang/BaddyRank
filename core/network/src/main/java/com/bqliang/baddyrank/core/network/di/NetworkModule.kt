package com.bqliang.baddyrank.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    const val BASE_URL = "https://raw.githubusercontent.com/bqliang/BaddyRank/refs/heads/master/"

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun providesHttpClient(json: Json): HttpClient = HttpClient(OkHttp) {

        defaultRequest {
            url(BASE_URL)
        }

        install(ContentNegotiation) {
            json(json)
        }

        install(HttpTimeout) {

        }

        install(Logging) {

        }
    }
}


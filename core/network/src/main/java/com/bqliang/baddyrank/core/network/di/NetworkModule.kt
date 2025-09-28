package com.bqliang.baddy.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true // 忽略未知属性
        prettyPrint = true
    }

    @Provides
    @Singleton
    fun provideKtorHttpClient(
        json: Json,
    ) : HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json
            }
        }
    }
}

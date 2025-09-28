@file:OptIn(ExperimentalSerializationApi::class)

package com.bqliang.baddy.crawler.captcha

import com.bqliang.baddy.crawler.util.saveDataAsJson
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.io.File

interface CaptchaDataCache {
    suspend fun saveCacheCaptchaData(question: String, answer: String)
    suspend fun getCacheCaptchaAnswer(question: String): String?
    suspend fun removeCacheCaptchaData(question: String)
}

class CaptchaDataCacheImpl(json: Json) : CaptchaDataCache {

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    private var cache: MutableMap<String, String> = mutableMapOf()
    private val cacheFile = File("captcha_data.json")

    init {
        runCatching {
            cacheFile.inputStream().use { inputStream ->
                val qAndA: Map<String, String> = json.decodeFromStream(inputStream)
                cache.putAll(qAndA)
            }
        }.onFailure { e ->
            logger.error(e) { "Failed to load captcha data cache" }
        }
    }

    override suspend fun saveCacheCaptchaData(question: String, answer: String) {
        if (cache[question] == answer) {
            return
        }
        cache[question] = answer
        cache.saveDataAsJson(cacheFile)
    }

    override suspend fun getCacheCaptchaAnswer(question: String): String? {
        return cache[question]
    }

    override suspend fun removeCacheCaptchaData(question: String) {
        if (cache[question] == null) {
            return
        }
        cache.remove(question)
        cache.saveDataAsJson(cacheFile)
    }
}

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
            logger.info { "try to save [$question -> $answer] to cache, but it already exists" }
            return
        }
        cache[question] = answer
        cache.saveDataAsJson(cacheFile)

        logger.info { "save [$question -> $answer] to cache successfully" }
    }

    override suspend fun getCacheCaptchaAnswer(question: String): String? {
        return cache[question].also { answer ->
            logger.debug {
                if (answer == null) "未命中缓存: $question"
                else "命中缓存: $question -> $answer"
            }
        }
    }

    override suspend fun removeCacheCaptchaData(question: String) {
        val answer = cache.remove(question)
        if (answer == null) {
            logger.info { "try to remove [$question -> $answer] from cache, but it doesn't exist" }
        } else {
            cache.saveDataAsJson(cacheFile)
            logger.info { "remove [$question -> $answer] from cache successfully" }
        }
    }
}

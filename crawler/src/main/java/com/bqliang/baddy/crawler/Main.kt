@file:OptIn(ExperimentalSerializationApi::class)

package com.bqliang.baddy.crawler

import com.bqliang.baddy.crawler.di.appModule
import com.bqliang.baddy.crawler.model.MatchType
import com.bqliang.baddy.crawler.model.RankingType
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.context.startKoin
import java.io.File

private val logger = KotlinLogging.logger {  }

fun main(): Unit = runBlocking {
    val koinApp = startKoin {
        printLogger()
        modules(appModule)
    }
    val koin = koinApp.koin

    val crawlerService: CrawlerService = koin.get()
    crawlerService.ensureSessionIsValid()

    RankingType.entries.forEach { rankingType ->
        MatchType.entries.forEach { matchType ->
            launch(Dispatchers.Default) {
                val rankings = crawlerService.fetchRanking(rankingType, matchType)

                val dir = File("result" + File.separator + rankingType.lowercaseName)
                dir.mkdirs()
                val jsonFile = File(dir, matchType.lowercaseName + ".json")
                jsonFile.createNewFile()
                rankings.saveDataAsJson(jsonFile)
            }
        }
    }
}


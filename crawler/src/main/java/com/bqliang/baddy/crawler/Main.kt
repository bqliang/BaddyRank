@file:OptIn(ExperimentalSerializationApi::class, DelicateCoroutinesApi::class)

package com.bqliang.baddy.crawler

import com.bqliang.baddy.crawler.captcha.InvalidDataException
import com.bqliang.baddy.crawler.di.appModule
import com.bqliang.baddy.crawler.model.Discipline
import com.bqliang.baddy.crawler.model.RankingCategory
import com.bqliang.baddy.crawler.util.saveDataAsJson
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.context.startKoin
import java.io.File
import java.time.Year

private val logger = KotlinLogging.logger {  }

fun main(): Unit = runBlocking {
    val koinApp = startKoin {
        printLogger()
        modules(appModule)
    }
    val koin = koinApp.koin

    val crawlerService: CrawlerService = koin.get()
    crawlerService.ensureSessionIsValid()

    RankingCategory.entries.forEach { rankingCategory ->
        Discipline.entries.forEach { discipline ->
            val dir =
                File("result" + File.separator + rankingCategory.lowercaseName + File.separator + discipline.lowercaseName)
            if (!dir.exists()) return@forEach
            val latestYear =
                dir
                    .listFiles { file -> file.isDirectory }
                    ?.maxOfOrNull { file -> file.name.toInt() }
                    ?: 1990

            val yearDir = File(dir, latestYear.toString())
            val latestWeek =
                yearDir
                    .listFiles { file -> file.isFile }
                    ?.maxOfOrNull { file -> file.nameWithoutExtension.toInt() }
                    ?: 1

            val years = (latestYear..Year.now().value)
            val weeks = (latestWeek..53)

            years.forEach { year ->
                weeks.forEach { week ->
                    val paddedWeek = week.toString().padStart(2, '0')
                    val rw = "$year/$paddedWeek}"
                    while (true) {
                        runCatching {
                            val ranking =
                                crawlerService.fetchRanking(rankingCategory, discipline, rw)
                            val jsonFile =
                                File(dir, year.toString() + File.separator + paddedWeek + ".json")
                            ranking.saveDataAsJson(jsonFile)
                        }.onFailure { e ->
                            if (e is InvalidDataException) break
                        }.onSuccess {
                            break
                        }
                    }
                }
            }
        }
    }
}


package com.bqliang.baddy.crawler.util

import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.RankingCategory
import com.bqliang.baddyrank.core.network.data.YearAvailabilityDto
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

private val logger = KotlinLogging.logger { }

suspend fun buildIndexJsonFromDir(dir: File): Unit = withContext(Dispatchers.IO) {
    if (!dir.exists() || !dir.isDirectory) {
        logger.warn { "Warning: Root directory '$dir' does not exist. Returning empty data." }
        return@withContext
    }

    val availableData: List<YearAvailabilityDto>? = dir
        .listFiles { file -> file.isDirectory }
        ?.mapNotNull { yearDir/* men_singles, men_doubles... */ ->
            val year = yearDir.name.toIntOrNull() ?: return@mapNotNull null
            val weeks: List<Int>? = yearDir
                .listFiles { file -> file.isFile && file.extension == "json" }
                ?.mapNotNull { jsonFile ->
                    val week = jsonFile.nameWithoutExtension.toIntOrNull() ?: return@mapNotNull null
                    week
                }?.sorted()

            if (weeks.isNullOrEmpty()) return@mapNotNull null

            YearAvailabilityDto(year, weeks)
        }?.sortedBy { it.year }

    if (availableData.isNullOrEmpty()) {
        logger.warn { "Warning: No valid data found in directory '$dir'" }
        return@withContext
    }

    val indexFile = File(dir, "index.json")
    availableData.saveDataAsJson(indexFile)
}

suspend fun buildAllIndexJson() = withContext(Dispatchers.Default) {
    RankingCategory.entries.forEach { rankingCategory ->
        Discipline.entries.forEach { discipline ->
            val dir = File("result" + File.separator + rankingCategory.lowercaseName + File.separator + discipline.lowercaseName)
            launch {
                buildIndexJsonFromDir(dir)
            }
        }
    }
}

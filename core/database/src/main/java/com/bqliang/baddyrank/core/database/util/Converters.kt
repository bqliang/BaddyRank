package com.bqliang.baddyrank.core.database.util

import androidx.room.TypeConverter
import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.RankingCategory

internal class Converters {

    @TypeConverter
    fun fromIntList(weeks: List<Int>): String = weeks.joinToString(separator = ",", prefix = "[", postfix = "]")

    @TypeConverter
    fun toIntList(weeksJson: String): List<Int> = weeksJson.removeSurrounding("[", "]").split(",").map { it.toInt() }

    @TypeConverter
    fun fromRankingCategory(rankingCategory: RankingCategory): String = rankingCategory.name

    @TypeConverter
    fun toRankingCategory(rankingCategory: String): RankingCategory = RankingCategory.valueOf(rankingCategory)

    @TypeConverter
    fun fromDiscipline(discipline: Discipline): String = discipline.name

    @TypeConverter
    fun toDiscipline(discipline: String): Discipline = Discipline.valueOf(discipline)
}

package com.bqliang.baddyrank.core.database.util

import androidx.room.TypeConverter

internal class ListIntConverters {

    /**
     * 将一个整数列表 (List<Int>) 转换为 JSON 格式的字符串
     * Room 将在写入数据库时调用此方法
     * e.g., listOf(1, 2, 3) -> "[1,2,3]"
     */
    @TypeConverter
    fun fromIntList(weeks: List<Int>): String = weeks.joinToString(separator = ",", prefix = "[", postfix = "]")

    /**
     * 将 JSON 格式的字符串转换回整数列表 (List<Int>)
     * Room 将在从数据库读取数据时调用此方法
     * e.g., "[1,2,3]" -> listOf(1, 2, 3)
     */
    @TypeConverter
    fun toIntList(weeksJson: String): List<Int> = weeksJson.removeSurrounding("[", "]").split(",").map { it.toInt() }
}

package com.bqliang.baddyrank.core.database.model

import androidx.room.Entity

/**
 * 缓存特定排名类型和项目下，某一年份可用的数据周数
 *
 * @property category 排名类型 (e.g., "WORLD_RANKING")
 * @property discipline 比赛项目 (e.g., "MEN_SINGLES")
 * @property year 年份, e.g., 2025
 * @property weeks 该年份下所有可用的周数列表
 */
@Entity(
    tableName = "year_availability",
    // 创建一个复合主键，确保每个排名分类下每年的周数据只有一条记录
    primaryKeys = ["category", "discipline", "year"]
)
data class YearAvailabilityEntity(
    val category: String,
    val discipline: String,
    val year: Int,
    val weeks: List<Int>,
)
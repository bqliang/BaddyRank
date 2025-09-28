package com.bqliang.baddyrank.core.database.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 排名信息实体类，用于 Room 数据库缓存
 *
 * 添加了 category, discipline, year, week 字段以支持查询
 * 添加了复合唯一索引 (`unique_ranking_identifier`) 来确保每个时间点的特定排名数据只存储一次
 */
@Entity(
    tableName = "rankings",
    indices = [
        Index(
            value = ["category", "discipline", "year", "week"],
            name = "idx_ranking_query"
        ),
    ]
)
data class RankingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val rank: Int,
    val rankTrend: Int,
    val tournaments: Int?,
    val points: Int,
    val protectedRank: Boolean = false,
    val category: String, // e.g., "WORLD_RANKING"
    val discipline: String, // e.g., "MEN_SINGLES"
    val year: Int,
    val week: Int,
)
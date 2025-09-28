package com.bqliang.baddyrank.core.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 运动员实体类，用于 Room 数据库缓存
 *
 * @property id 运动员的唯一标识符，作为主键
 * @property countryAbbreviation 国籍缩写
 * @property countryFlagUrl 国旗图片的 URL
 * @property avatarUrl 运动员头像图片的 URL
 * @property chineseName 运动员中文名
 * @property englishName 运动员英文名
 */
@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey
    val id: String,
    val countryAbbreviation: String,
    val countryFlagUrl: String,
    val avatarUrl: String,
    val chineseName: String,
    val englishName: String
)
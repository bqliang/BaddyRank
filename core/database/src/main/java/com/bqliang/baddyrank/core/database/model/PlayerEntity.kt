package com.bqliang.baddyrank.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bqliang.baddyrank.core.model.data.Player

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
    @ColumnInfo(name = "country_abbreviation")
    val countryAbbreviation: String,
    @ColumnInfo(name = "country_flag_url")
    val countryFlagUrl: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    val chineseName: String,
    val englishName: String
)

fun PlayerEntity.asExternalModel(): Player = Player(
    id = id,
    countryAbbreviation = countryAbbreviation,
    countryFlag = countryFlagUrl,
    avatar = avatarUrl,
    chineseName = chineseName,
    englishName = englishName,
)
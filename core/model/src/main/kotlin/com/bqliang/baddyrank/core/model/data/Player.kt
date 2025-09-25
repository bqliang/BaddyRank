package com.bqliang.baddyrank.core.model.data


/**
 * 羽毛球运动员
 *
 * @property id 唯一标识符
 * @property countryAbbreviation 国籍缩写
 * @property countryFlagUrl 国旗图片 URL
 * @property avatarUrl 运动员头像图片 URL
 * @property chineseName 运动员中文名
 * @property englishName 运动员英文名
 */
data class Player(
    val id: String,
    val countryAbbreviation: String,
    val countryFlagUrl: String,
    val avatarUrl: String,
    val chineseName: String,
    val englishName: String,
)
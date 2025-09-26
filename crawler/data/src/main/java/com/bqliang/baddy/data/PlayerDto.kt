package com.bqliang.baddy.data

import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val id: String,
    val countryAbbreviation: String,
    val countryFlagUrl: String,
    val avatarUrl: String,
    val chineseName: String,
    val englishName: String,
)

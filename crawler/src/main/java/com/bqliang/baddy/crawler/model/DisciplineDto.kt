package com.bqliang.baddy.crawler.model

import kotlinx.serialization.Serializable

@Serializable
enum class DisciplineDto(val code: Int, val description: String) {
    MEN_SINGLES(6, "男子单打"),
    WOMEN_SINGLES(7, "女子单打"),
    MEN_DOUBLES(8, "男子双打"),
    WOMEN_DOUBLES(9, "女子双打"),
    MIXED_DOUBLES(10, "混合双打");

    val lowercaseName: String
        get() = name.lowercase()

    companion object {
        fun fromCode(code: Int): DisciplineDto? = entries.find { it.code == code }
    }
}

package com.bqliang.baddy.crawler.model

enum class RankingCategoryDto(val path: String, val description: String) {
    // 世界排名 - 代码 1
    WORLD_RANKING("ranking", "世界排名"),

    // 巡回赛排名 - 代码 2
    // TOUR_RANKING("ranking_finals", "巡回赛排名"),

    // 青年排名 - 代码 3
    JUNIOR_RANKING("ranking_junior", "青年排名");

    // 奥运积分排名 - 代码 4
    // OLYMPIC_QUALIFICATION("olympic2024", "奥运积分排名");

    val lowercaseName: String
        get() = name.lowercase()
}
package com.bqliang.baddyrank.core.ui.model

internal enum class TableColumn(val title: String, val weight: Float) {
    Rank("排名", 0.22f),
    Country("国籍", 0.22f),
    Player("球员", 1f),
    RankTrend("升降", 0.28f),
    Tournaments("站数", 0.28f),
    Points("积分", 0.28f),

}
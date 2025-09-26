package com.bqliang.baddy.data

import kotlinx.serialization.Serializable

@Serializable
data class Ranking(
    val rank: Int,
    val players: List<Player>,
    val rankTrend: Int,
    val tournaments: Int? = null,
    val points: Int,
    val protectedRank: Boolean = false,
)

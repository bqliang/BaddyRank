package com.bqliang.baddyrank.core.network.data

import kotlinx.serialization.Serializable

@Serializable
data class RankingDto(
    val rank: Int,
    val players: List<PlayerDto>,
    val rankTrend: Int,
    val tournaments: Int? = null,
    val points: Int,
    val protectedRank: Boolean = false,
)

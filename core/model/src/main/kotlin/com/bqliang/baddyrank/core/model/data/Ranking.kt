package com.bqliang.baddyrank.core.model.data

/**
 * 排名
 *
 * @property rank 排名
 * @property players 球员列表
 * @property rankTrend 排名趋势（正数为上升，负数为下降，0为不变）
 * @property tournaments 站数
 * @property points 积分
 * @property protectedRank 排名保护
 */
data class Ranking(
    val rank: Int,
    val players: List<Player>,
    val rankTrend: Int,
    val tournaments: Int? = null,
    val points: Int,
    val protectedRank: Boolean = false,
)

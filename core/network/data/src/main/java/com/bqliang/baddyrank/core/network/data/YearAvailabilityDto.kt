package com.bqliang.baddyrank.core.network.data

import kotlinx.serialization.Serializable

/**
 * 描述了单个年份下可用的数据周数。
 * @param year 年份 (e.g., 2025)。
 * @param weeks 该年份下所有可用的周数列表。
 */
@Serializable
data class YearAvailabilityDto(
    val year: Int,
    val weeks: List<Int>,
)
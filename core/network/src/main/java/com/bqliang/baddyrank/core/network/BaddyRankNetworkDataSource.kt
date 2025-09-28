package com.bqliang.baddyrank.core.network

import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.RankingCategory
import com.bqliang.baddyrank.core.network.data.RankingDto
import com.bqliang.baddyrank.core.network.data.YearAvailabilityDto

interface BaddyRankNetworkDataSource {
    suspend fun getRanking(
        category: RankingCategory,
        discipline: Discipline,
        year: Int,
        week: Int,
    ): List<RankingDto>

    suspend fun getAvailabilityData(
        category: RankingCategory,
        discipline: Discipline,
    ): List<YearAvailabilityDto>
}

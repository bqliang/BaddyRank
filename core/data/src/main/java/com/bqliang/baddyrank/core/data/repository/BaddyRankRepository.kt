package com.bqliang.baddyrank.core.data.repository

import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.Ranking
import com.bqliang.baddyrank.core.model.data.RankingCategory
import com.bqliang.baddyrank.core.model.data.YearAvailability
import kotlinx.coroutines.flow.Flow


interface BaddyRankRepository {

    suspend fun syncRankingAvailability() : Boolean

    fun getRankingAvailability(category: RankingCategory, discipline: Discipline): Flow<List<YearAvailability>>

    fun getRanking(category: RankingCategory, discipline: Discipline, year: Int, week: Int): Flow<List<Ranking>>
}
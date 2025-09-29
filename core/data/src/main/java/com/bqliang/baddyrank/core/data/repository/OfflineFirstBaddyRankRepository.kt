package com.bqliang.baddyrank.core.data.repository

import android.util.Log
import com.bqliang.baddyrank.core.data.model.asEntity
import com.bqliang.baddyrank.core.database.dao.RankingDao
import com.bqliang.baddyrank.core.database.model.YearAvailabilityEntity
import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.RankingCategory
import com.bqliang.baddyrank.core.network.BaddyRankNetworkDataSource
import com.bqliang.baddyrank.core.network.data.YearAvailabilityDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class OfflineFirstBaddyRankRepository @Inject constructor(
    private val rankingDao: RankingDao,
    private val network: BaddyRankNetworkDataSource,
) : BaddyRankRepository {


    override suspend fun syncRankingAvailability(): Boolean = withContext(Dispatchers.IO) {
        RankingCategory.entries.map { rankingCategory ->
            Discipline.entries.map { discipline ->
                async {
                    runCatching {
                        val rankingAvailabilityData: List<YearAvailabilityDto> =
                            network.getAvailabilityData(rankingCategory, discipline)
                        val rankingAvailabilityEntities: List<YearAvailabilityEntity> =
                            rankingAvailabilityData.map {
                                it.asEntity(rankingCategory, discipline)
                            }
                        rankingDao.insertYearAvailabilities(rankingAvailabilityEntities)
                    }
                        .isSuccess
                }
            }
        }.flatten().awaitAll().all { isSuccess -> isSuccess }.also {
            Log.e("bqliang", "syncRankingAvailability - $it")
        }
    }
}
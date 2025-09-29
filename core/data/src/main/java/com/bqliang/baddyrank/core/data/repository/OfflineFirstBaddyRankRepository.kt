package com.bqliang.baddyrank.core.data.repository

import com.bqliang.baddyrank.core.data.model.asEntity
import com.bqliang.baddyrank.core.data.model.playerEntityShells
import com.bqliang.baddyrank.core.database.dao.RankingDao
import com.bqliang.baddyrank.core.database.model.PlayerEntity
import com.bqliang.baddyrank.core.database.model.RankingWithPlayers
import com.bqliang.baddyrank.core.database.model.YearAvailabilityEntity
import com.bqliang.baddyrank.core.database.model.asExternalModel
import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.Ranking
import com.bqliang.baddyrank.core.model.data.RankingCategory
import com.bqliang.baddyrank.core.model.data.YearAvailability
import com.bqliang.baddyrank.core.network.BaddyRankNetworkDataSource
import com.bqliang.baddyrank.core.network.data.RankingDto
import com.bqliang.baddyrank.core.network.data.YearAvailabilityDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
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

                        val latestAvailability: YearAvailabilityEntity? =
                            rankingDao.getYearAvailabilities(rankingCategory.name, discipline.name)
                                .firstOrNull()
                                ?.firstOrNull()

                        if (latestAvailability == null) return@runCatching

                        val latestWeek = latestAvailability.weeks.last()
                        val latestRanking = rankingDao.getRanking(
                            rankingCategory.name,
                            discipline.name,
                            latestAvailability.year,
                            latestWeek,
                        ).firstOrNull()

                        if (latestRanking.isNullOrEmpty()) {
                            val rankings = network.getRanking(
                                rankingCategory,
                                discipline,
                                latestAvailability.year,
                                latestWeek,
                            )

                            saveRankings(
                                rankings,
                                rankingCategory,
                                discipline,
                                latestAvailability.year,
                                latestWeek
                            )
                        }
                    }
                        .isSuccess
                }
            }
        }.flatten().awaitAll().all { isSuccess -> isSuccess }
    }


    override fun getRankingAvailability(
        category: RankingCategory,
        discipline: Discipline,
    ): Flow<List<YearAvailability>> =
        rankingDao.getYearAvailabilities(
            category.name,
            discipline.name
        )
            .map { yearAvailabilityEntities ->
                yearAvailabilityEntities.map(YearAvailabilityEntity::asExternalModel)
            }

    override fun getRanking(
        category: RankingCategory,
        discipline: Discipline,
        year: Int,
        week: Int,
    ): Flow<List<Ranking>> =
        rankingDao.getRanking(
            category.name,
            discipline.name,
            year,
            week,
        ).onEmpty {
            val rankingsDto = network.getRanking(category, discipline, year, week)
            saveRankings(rankingsDto, category, discipline, year, week)
        }
            .map { rankingsWithPlayers ->
                rankingsWithPlayers.map(RankingWithPlayers::asExternalModel)
            }


    private suspend fun saveRankings(
        rankingsDto: List<RankingDto>,
        rankingCategory: RankingCategory,
        discipline: Discipline,
        year: Int,
        week: Int,
    ) = withContext(Dispatchers.IO) {
        val rankingEntities = rankingsDto.map { rankingDto ->
            val rankingEntity = rankingDto.asEntity(
                rankingCategory,
                discipline,
                year,
                week,
            )
            val playerEntities: List<PlayerEntity> = rankingDto.playerEntityShells()
            rankingEntity to playerEntities
        }
        rankingDao.insertRankingsWithPlayers(rankingEntities)
    }
}
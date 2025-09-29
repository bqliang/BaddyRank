package com.bqliang.baddyrank.core.data.repository

import com.bqliang.baddyrank.core.database.dao.RankingDao
import com.bqliang.baddyrank.core.network.BaddyRankNetworkDataSource
import javax.inject.Inject

internal class OfflineFirstBaddyRankRepository @Inject constructor(
    private val rankingDao: RankingDao,
    private val network: BaddyRankNetworkDataSource,
) : BaddyRankRepository {

}
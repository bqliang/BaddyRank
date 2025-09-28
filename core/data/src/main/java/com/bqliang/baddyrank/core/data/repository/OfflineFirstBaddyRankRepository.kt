package com.bqliang.baddyrank.core.data.repository

import com.bqliang.baddyrank.core.database.dao.RankingDao
import javax.inject.Inject

internal class OfflineFirstBaddyRankRepository @Inject constructor(
    private val rankingDao: RankingDao
) : BaddyRankRepository {

}
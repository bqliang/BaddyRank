package com.bqliang.baddyrank.core.database.di

import com.bqliang.baddyrank.core.database.BaddyRankDatabase
import com.bqliang.baddyrank.core.database.dao.RankingDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesTopicsDao(
        database: BaddyRankDatabase,
    ): RankingDao = database.rankingDao()
}

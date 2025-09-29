package com.bqliang.baddyrank.core.data.di

import com.bqliang.baddyrank.core.data.repository.BaddyRankRepository
import com.bqliang.baddyrank.core.data.repository.OfflineFirstBaddyRankRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    internal abstract fun bindsBaddyRankRepository(
        topicsRepository: OfflineFirstBaddyRankRepository,
    ): BaddyRankRepository
}

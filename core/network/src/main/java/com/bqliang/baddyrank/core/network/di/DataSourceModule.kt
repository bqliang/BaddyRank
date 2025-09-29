package com.bqliang.baddyrank.core.network.di

import com.bqliang.baddyrank.core.network.BaddyRankNetworkDataSource
import com.bqliang.baddyrank.core.network.ktor.KtorBaddyRankNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {
    @Binds
    fun binds(impl: KtorBaddyRankNetwork): BaddyRankNetworkDataSource
}
package com.bqliang.baddyrank.core.data.repository


interface BaddyRankRepository {
    suspend fun syncRankingAvailability() : Boolean
}
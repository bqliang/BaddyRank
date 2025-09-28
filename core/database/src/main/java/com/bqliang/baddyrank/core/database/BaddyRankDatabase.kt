package com.bqliang.baddyrank.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bqliang.baddyrank.core.database.dao.RankingDao
import com.bqliang.baddyrank.core.database.model.PlayerEntity
import com.bqliang.baddyrank.core.database.model.RankingEntity
import com.bqliang.baddyrank.core.database.model.RankingPlayerCrossRef

@Database(
    entities = [
        RankingEntity::class,
        PlayerEntity::class,
        RankingPlayerCrossRef::class
    ],
    version = 1,
    exportSchema = false // 在初期开发阶段可以设为 false，如果需要做数据库迁移则需设为 true
)
abstract class BaddyRankDatabase : RoomDatabase() {
    abstract fun rankingDao(): RankingDao
}
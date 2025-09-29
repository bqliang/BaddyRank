package com.bqliang.baddyrank.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bqliang.baddyrank.core.database.model.PlayerEntity
import com.bqliang.baddyrank.core.database.model.RankingEntity
import com.bqliang.baddyrank.core.database.model.RankingPlayerCrossRef
import com.bqliang.baddyrank.core.database.model.RankingWithPlayers
import com.bqliang.baddyrank.core.database.model.YearAvailabilityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RankingDao {

    /**
     * 根据指定的条件查询排名数据，并返回包含运动员信息的结果。
     * 使用 Flow 包装，以便在数据变化时自动收到更新。
     * @Transaction 注解确保 "获取 Ranking" 和 "获取关联的 Players" 这两个步骤是原子性操作。
     */
    @Transaction
    @Query("""
        SELECT * FROM rankings 
        WHERE category = :category 
        AND discipline = :discipline 
        AND year = :year 
        AND week = :week
        ORDER BY rank ASC
        LIMIT 1
    """)
    fun getRanking(
        category: String,
        discipline: String,
        year: Int,
        week: Int
    ): Flow<RankingWithPlayers>

    /**
     * 插入一条排名记录，并返回它在数据库中的自增 ID
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRanking(ranking: RankingEntity): Long

    /**
     * 批量插入运动员。
     * OnConflictStrategy.IGNORE 表示如果运动员已存在 (主键 id 相同)，则忽略此次插入。
     * 这可以避免重复存储同一个运动员的信息。
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlayers(players: List<PlayerEntity>)

    /**
     * 批量插入排名与运动员的关联关系
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRankingPlayerCrossRefs(crossRefs: List<RankingPlayerCrossRef>)

    /**
     * 批量插入或替换可用年份的周数据
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertYearAvailabilities(data: List<YearAvailabilityEntity>)

    /**
     * 根据排名类型和项目，查询所有已缓存的可用年份和周数据
     * @return 一个数据流，当可用周数据变化时会自动更新
     */
    @Query("""
        SELECT * FROM year_availability
        WHERE category = :category AND discipline = :discipline
        ORDER BY year DESC
    """)
    fun getYearAvailabilities(
        category: String,
        discipline: String
    ): Flow<List<YearAvailabilityEntity>>
}
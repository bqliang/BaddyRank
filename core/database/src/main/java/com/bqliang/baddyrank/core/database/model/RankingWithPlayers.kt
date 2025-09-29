package com.bqliang.baddyrank.core.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bqliang.baddyrank.core.model.data.Ranking

/**
 * 用于封装一个 RankingEntity 及其关联的多个 PlayerEntity 的关系类
 *
 * @property ranking 内嵌的排名实体
 * @property players 与该排名关联的运动员列表
 */
data class RankingWithPlayers(
    @Embedded
    val ranking: RankingEntity,
    @Relation(
        entity = PlayerEntity::class,
        parentColumn = "id", // RankingEntity 的主键
        entityColumn = "id",   // PlayerEntity 的主键
        associateBy = Junction(
            value = RankingPlayerCrossRef::class,
            parentColumn = "ranking_id",
            entityColumn = "player_id",
        )
    )
    val players: List<PlayerEntity>
)

fun RankingWithPlayers.asExternalModel(): Ranking = Ranking(
    rank = ranking.rank,
    players = players.map(PlayerEntity::asExternalModel),
    rankTrend = ranking.rankTrend,
    tournaments = ranking.tournaments,
    points = ranking.points,
    protectedRank = ranking.protectedRank,
)
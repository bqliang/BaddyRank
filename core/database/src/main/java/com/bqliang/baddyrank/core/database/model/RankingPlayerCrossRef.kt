package com.bqliang.baddyrank.core.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

/**
 * Ranking 和 Player 的交叉引用表，用于建立多对多关系
 *
 * @property rankingId 关联的排名信息的 ID
 * @property playerId 关联的运动员的 ID
 */
@Entity(
    tableName = "ranking_player_cross_ref",
    primaryKeys = ["rankingId", "playerId"],
    foreignKeys = [
        ForeignKey(
            entity = RankingEntity::class,
            parentColumns = ["id"],
            childColumns = ["rankingId"]
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"]
        )
    ],
    indices = [
        Index(value = ["rankingId"]),
        Index(value = ["playerId"])
    ]
)
data class RankingPlayerCrossRef(
    val rankingId: Long,
    val playerId: String
)

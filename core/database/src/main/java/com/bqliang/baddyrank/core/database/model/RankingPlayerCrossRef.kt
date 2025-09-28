package com.bqliang.baddyrank.core.database.model

import androidx.room.ColumnInfo
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
    primaryKeys = ["ranking_id", "player_id"],
    foreignKeys = [
        ForeignKey(
            entity = RankingEntity::class,
            parentColumns = ["id"],
            childColumns = ["ranking_id"],
        ),
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["player_id"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [
        Index(value = ["ranking_id"]),
        Index(value = ["player_id"]),
    ]
)
data class RankingPlayerCrossRef(
    @ColumnInfo(name = "ranking_id")
    val rankingId: Long,
    @ColumnInfo(name = "player_id")
    val playerId: String,
)

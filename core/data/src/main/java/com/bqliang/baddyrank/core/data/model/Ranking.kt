package com.bqliang.baddyrank.core.data.model

import com.bqliang.baddyrank.core.database.model.PlayerEntity
import com.bqliang.baddyrank.core.database.model.RankingEntity
import com.bqliang.baddyrank.core.database.model.RankingPlayerCrossRef
import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.Ranking
import com.bqliang.baddyrank.core.model.data.RankingCategory
import com.bqliang.baddyrank.core.network.data.RankingDto

fun RankingDto.playerCrossReferences(rankingId: Long): List<RankingPlayerCrossRef> {
    return players.map { player ->
        RankingPlayerCrossRef(
            rankingId = rankingId,
            playerId = player.id
        )
    }
}

fun RankingDto.playerEntityShells() : List<PlayerEntity> = players.map { player ->
    PlayerEntity(
        id = player.id,
        countryAbbreviation = player.countryAbbreviation,
        countryFlagUrl = player.countryFlagUrl,
        avatarUrl = player.avatarUrl,
        chineseName = player.chineseName,
        englishName = player.englishName,
    )
}

fun RankingDto.asEntity(
    rankingCategory: RankingCategory,
    discipline: Discipline,
    year: Int,
    week: Int,
): RankingEntity = RankingEntity(
    rank = rank,
    rankTrend = rankTrend,
    tournaments = tournaments,
    points = points,
    protectedRank = protectedRank,
    category = rankingCategory,
    discipline = discipline,
    year = year,
    week = week,
)
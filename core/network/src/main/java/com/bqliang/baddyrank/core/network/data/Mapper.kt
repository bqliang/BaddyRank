package com.bqliang.baddyrank.core.network.data

import com.bqliang.baddyrank.core.model.data.Player
import com.bqliang.baddyrank.core.model.data.Ranking

fun PlayerDto.asExternalModel(): Player =
    Player(
        id = id,
        countryAbbreviation = countryAbbreviation,
        countryFlag = countryFlagUrl,
        avatar = avatarUrl,
        chineseName = chineseName,
        englishName = englishName,
    )

fun RankingDto.asExternalModel(): Ranking =
    Ranking(
        rank = rank,
        players = players.map(PlayerDto::asExternalModel),
        rankTrend = rankTrend,
        tournaments = tournaments,
        points = points,
        protectedRank = protectedRank,
    )

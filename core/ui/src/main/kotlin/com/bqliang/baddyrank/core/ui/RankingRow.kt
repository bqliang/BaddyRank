@file:OptIn(ExperimentalUuidApi::class)

package com.bqliang.baddyrank.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.bqliang.baddyrank.core.model.data.Player
import com.bqliang.baddyrank.core.model.data.Ranking
import com.bqliang.baddyrank.core.ui.model.TableColumn
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private class RankingProvider : PreviewParameterProvider<Ranking> {
    override val values: Sequence<Ranking>
        get() = sequenceOf(
            Ranking(
                rank = 1,
                players = listOf(
                    Player(
                        id = Uuid.random().toString(),
                        countryAbbreviation = "CHN",
                        countryFlag = R.drawable.china,
                        avatar = R.drawable.shi_yu_qi,
                        chineseName = "石宇奇",
                        englishName = "Shi Yu Qi",
                    )
                ),
                rankTrend = 0,
                tournaments = 12,
                points = 12324,
                protectedRank = false,
            ),
            Ranking(
                rank = 1,
                players = listOf(
                    Player(
                        id = Uuid.random().toString(),
                        countryAbbreviation = "CHN",
                        countryFlag = R.drawable.china,
                        avatar = R.drawable.shi_yu_qi,
                        chineseName = "石宇奇",
                        englishName = "Shi Yu Qi",
                    ),
                    Player(
                        id = Uuid.random().toString(),
                        countryAbbreviation = "CHN",
                        countryFlag = R.drawable.china,
                        avatar = R.drawable.shi_yu_qi,
                        chineseName = "石宇奇",
                        englishName = "Shi Yu Qi",
                    )
                ),
                rankTrend = 2,
                tournaments = 12,
                points = 12324,
                protectedRank = true,
            )
        )
}


@Preview(showBackground = true)
@Composable
private fun RankingPreview(
    @PreviewParameter(RankingProvider::class) ranking: Ranking,
) {
    RankingRow(
        players = ranking.players,
        rank = ranking.rank,
        rankTrend = ranking.rankTrend,
        tournaments = ranking.tournaments,
        points = ranking.points,
        protectedRank = ranking.protectedRank,
    )
}

@Composable
fun RankingRow(
    modifier: Modifier = Modifier,
    players: List<Player>,
    rank: Int,
    rankTrend: Int,
    tournaments: Int? = null,
    points: Int,
    protectedRank: Boolean = false,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(TableColumn.Rank.weight),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                style = MaterialTheme.typography.labelLarge,
                text = rank.toString(),
            )

            if (protectedRank) {
                Icon(
                    modifier = Modifier.size(12.dp),
                    imageVector = Icons.Outlined.Lock,
                    contentDescription = null,
                )
            }
        }

        Column(
            modifier = Modifier.weight(TableColumn.Country.weight),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            players.forEach { player ->
                key(player.id) {
                    CountryCell(
                        countryAbbreviation = player.countryAbbreviation,
                        countryFlag = player.countryFlag,
                    )
                }
            }
        }

        Column(
            modifier = Modifier.weight(TableColumn.Player.weight),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            players.forEach { player ->
                key(player.id) {
                    PlayerCell(
                        avatar = player.avatar,
                        chineseName = player.chineseName,
                        englishName = player.englishName,
                    )
                }
            }
        }

        RankTrendCell(
            modifier = Modifier.weight(0.28f),
            rankTrend,
        )

        if (tournaments != null) {
            Text(
                modifier = Modifier.weight(TableColumn.Tournaments.weight),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall,
                text = tournaments.toString(),
            )
        }

        Text(
            modifier = Modifier.weight(TableColumn.Points.weight),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodySmall,
            text = points.toString()
        )
    }
}

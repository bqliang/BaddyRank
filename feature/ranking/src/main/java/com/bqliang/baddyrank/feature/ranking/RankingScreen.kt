package com.bqliang.baddyrank.feature.ranking

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bqliang.baddyrank.core.ui.RankingRow
import com.bqliang.baddyrank.core.ui.TableHeader
import com.bqliang.baddyrank.core.ui.model.TableColumn

@Composable
fun RankingScreen(
    modifier: Modifier = Modifier,
    viewModel: RankingViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle(emptyList())

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(

        ) {
            stickyHeader {
                TableHeader(
                    tableColumns =TableColumn.entries,
                )
            }

            items(state) {
                RankingRow(
                    players = it.players,
                    rank = it.rank,
                    rankTrend = it.rankTrend,
                    tournaments = it.tournaments,
                    points = it.points,
                    protectedRank = it.protectedRank,
                )
            }
        }
    }


}

@Composable
internal fun RankingScreen(
    modifier: Modifier = Modifier,
) {

}


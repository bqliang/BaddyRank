package com.bqliang.baddyrank.feature.ranking

import androidx.lifecycle.ViewModel
import com.bqliang.baddyrank.core.data.repository.BaddyRankRepository
import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.RankingCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val rankRepository: BaddyRankRepository,
): ViewModel() {
    var uiState = MutableStateFlow(RankingUiState())
        .flatMapConcat {
            rankRepository.getRankingAvailability(
                it.selectRankingCategory,
                it.selectDiscipline,
            )
        }
        .map {
            it.first()
        }.flatMapConcat {
            rankRepository.getRanking(
                RankingCategory.WORLD_RANKING,
                Discipline.MEN_SINGLES,
                it.year,
                it.weeks.last(),
            )
        }
}
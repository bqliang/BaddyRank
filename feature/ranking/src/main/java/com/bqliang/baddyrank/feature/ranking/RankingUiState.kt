package com.bqliang.baddyrank.feature.ranking

import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.RankingCategory
import java.time.Year

data class RankingUiState(
    val selectRankingCategory: RankingCategory = RankingCategory.WORLD_RANKING,
    val selectDiscipline: Discipline = Discipline.MEN_SINGLES,
    val selectYear: Int = Year.now().value,
    val selectWeek: Int = 1
)
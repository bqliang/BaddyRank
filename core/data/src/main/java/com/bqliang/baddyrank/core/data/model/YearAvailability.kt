package com.bqliang.baddyrank.core.data.model

import com.bqliang.baddyrank.core.database.model.YearAvailabilityEntity
import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.RankingCategory
import com.bqliang.baddyrank.core.network.data.YearAvailabilityDto

fun YearAvailabilityDto.asEntity(
    category: RankingCategory,
    discipline: Discipline,
): YearAvailabilityEntity {
    return YearAvailabilityEntity(
        category = category.name,
        discipline = discipline.name,
        year = year,
        weeks = weeks,
    )
}
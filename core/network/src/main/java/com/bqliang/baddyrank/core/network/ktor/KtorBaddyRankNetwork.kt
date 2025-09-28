package com.bqliang.baddyrank.core.network.ktor

import com.bqliang.baddyrank.core.model.data.Discipline
import com.bqliang.baddyrank.core.model.data.RankingCategory
import com.bqliang.baddyrank.core.network.BaddyRankNetworkDataSource
import com.bqliang.baddyrank.core.network.data.RankingDto
import com.bqliang.baddyrank.core.network.data.YearAvailabilityDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class KtorBaddyRankNetwork @Inject constructor(
    private val httpClient: HttpClient,
) : BaddyRankNetworkDataSource {
    override suspend fun getRanking(
        category: RankingCategory,
        discipline: Discipline,
        year: Int,
        week: Int,
    ): List<RankingDto> =
        httpClient.get {
            url {
                path(
                    "result",
                    category.lowercaseName,
                    discipline.lowercaseName,
                    year.toString(),
                    "$week.json"
                )
            }
        }.body()

    override suspend fun getAvailabilityData(
        category: RankingCategory,
        discipline: Discipline,
    ): List<YearAvailabilityDto> = httpClient.get {
        url {
            path(
                "result",
                category.lowercaseName,
                discipline.lowercaseName,
                "index.json"
            )
        }
    }.body()
}
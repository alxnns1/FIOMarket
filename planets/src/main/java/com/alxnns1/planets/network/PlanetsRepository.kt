package com.alxnns1.planets.network

import com.alxnns1.utils.ResultOf
import javax.inject.Inject

class PlanetsRepository @Inject constructor(
    private val planetsService: PlanetsService
) {

    suspend fun getPlanets(query: String): ResultOf<List<String>> {
        return try {
            val planetsResponse = planetsService.getPlanets()
            if (planetsResponse.isSuccessful && planetsResponse.body() != null) {
                val body = planetsResponse.body()!!
                    .filter {
                        it.PlanetNaturalId.contains(query, true) ||
                                it.PlanetName.contains(query, true)
                    }
                ResultOf.Success(body.map { it.PlanetName })
            } else {
                ResultOf.Error()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ResultOf.Error()
        }
    }
}
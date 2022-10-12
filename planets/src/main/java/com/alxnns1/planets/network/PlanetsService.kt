package com.alxnns1.planets.network

import retrofit2.Response
import retrofit2.http.GET

interface PlanetsService {

    @GET("planet/allplanets")
    suspend fun getPlanets(): Response<List<PlanetResponse>>
}
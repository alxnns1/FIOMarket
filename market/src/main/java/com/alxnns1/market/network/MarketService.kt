package com.alxnns1.market.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MarketService {

    @GET("localmarket/planet/{planet}")
    suspend fun getMarket(@Path("planet") planet: String): Response<MarketResponse>
}
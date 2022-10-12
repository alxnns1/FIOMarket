package com.alxnns1.fiomarket.di

import android.content.Context
import com.alxnns1.market.network.MarketService
import com.alxnns1.planets.network.PlanetsService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration

private const val cacheSize = 5L * 1024 * 1024
private const val baseUrl = "https://rest.fnar.net/"

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {

    @Provides
    fun providePlanetsService(@ApplicationContext appContext: Context): PlanetsService {
        val client = OkHttpClient().newBuilder().connectTimeout(Duration.ofMinutes(1))
            .cache(Cache(appContext.cacheDir, cacheSize))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(PlanetsService::class.java)
    }

    @Provides
    fun provideMarketService(): MarketService {
        val client = OkHttpClient().newBuilder().connectTimeout(Duration.ofMinutes(1))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(MarketService::class.java)
    }
}
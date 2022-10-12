package com.alxnns1.market

import com.alxnns1.market.network.MarketRepository
import com.alxnns1.utils.ResultOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MarketViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMarketAds with planet calls API and emits result`() = runTest {
        val marketResult = ResultOf.Success(listOf(mockk<BuyAd>()))
        val marketRepository = mockk<MarketRepository> {
            coEvery { getMarket("planet") } returns marketResult
        }

        val subject = MarketViewModel(marketRepository)

        subject.getMarketAds("planet")
        advanceUntilIdle()

        coVerify { marketRepository.getMarket("planet") }
        Assert.assertEquals(marketResult, subject.marketState.value)
    }
}
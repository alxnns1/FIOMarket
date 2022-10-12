package com.alxnns1.market.network

import com.alxnns1.market.BuyAd
import com.alxnns1.market.SellAd
import com.alxnns1.market.ShippingAd
import com.alxnns1.utils.DateUtils
import com.alxnns1.utils.NumberFormatter
import com.alxnns1.utils.ResultOf
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class MarketRepositoryTest {

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
    fun `getMarket on success returns list of market ads sorted by expiry time`() = runTest {
        val marketService = mockk<MarketService> {
            coEvery { getMarket("planet") } returns marketResponse
        }
        val dateUtils = mockk<DateUtils> {
            every { timeRemaining(1L) } returns "1"
            every { timeRemaining(2L) } returns "2"
            every { timeRemaining(3L) } returns "3"
        }
        val numberFormatter = mockk<NumberFormatter> {
            every { format(0.123) } returns "0.123"
            every { format(1.23) } returns "1.23"
            every { format(12.3) } returns "12.3"
            every { format(10) } returns "10"
        }

        val subject = MarketRepository(marketService, dateUtils, numberFormatter)

        val actual = subject.getMarket("planet")
        advanceUntilIdle()

        assertEquals(marketAds, actual)
    }

    @Test
    fun `getMarket on error returns error with message`() = runTest {
        val marketResponse = Response.error<MarketResponse>(400, mockk(relaxed = true))
        val marketService = mockk<MarketService> {
            coEvery { getMarket("planet") } returns marketResponse
        }
        val dateUtils = mockk<DateUtils>()
        val numberFormatter = mockk<NumberFormatter>()

        val subject = MarketRepository(marketService, dateUtils, numberFormatter)

        val actual = subject.getMarket("planet")
        advanceUntilIdle()

        val expected = ResultOf.Error("No market found for planet")

        assertEquals(expected, actual)
    }

    private val marketResponse = Response.success(
        MarketResponse(
            listOf(
                ShippingAdResponse(
                    ContractNaturalId = 123,
                    PlanetNaturalId = "planetId",
                    CreatorCompanyName = "company",
                    OriginPlanetNaturalId = "planetId",
                    OriginPlanetName = "planet",
                    DestinationPlanetNaturalId = "destinationId",
                    DestinationPlanetName = "destination",
                    CargoWeight = 0.123,
                    CargoVolume = 1.23,
                    PayoutPrice = 12.3,
                    PayoutCurrency = "$",
                    ExpiryTimeEpochMs = 2L
                )
            ),
            listOf(
                TradeAdResponse(
                    ContractNaturalId = 234,
                    PlanetNaturalId = "planetId",
                    CreatorCompanyName = "company",
                    MaterialTicker = "material",
                    MaterialAmount = 10,
                    Price = 12.3,
                    PriceCurrency = "$",
                    ExpiryTimeEpochMs = 1L
                )
            ),
            listOf(
                TradeAdResponse(
                    ContractNaturalId = 345,
                    PlanetNaturalId = "planetId",
                    CreatorCompanyName = "company",
                    MaterialTicker = "material",
                    MaterialAmount = 10,
                    Price = 12.3,
                    PriceCurrency = "$",
                    ExpiryTimeEpochMs = 3L
                )
            )
        )
    )

    private val marketAds = ResultOf.Success(
        listOf(
            BuyAd(
                contractId = "planetId/234",
                expiryTime = 1L,
                timeRemaining = "1",
                creatorCompany = "company",
                price = "12.3",
                currency = "$",
                material = "material",
                quantity = "10"
            ),
            ShippingAd(
                contractId = "planetId/123",
                expiryTime = 2L,
                timeRemaining = "2",
                creatorCompany = "company",
                price = "12.3",
                currency = "$",
                weight = "0.123",
                volume = "1.23",
                origin = "planet",
                destination = "destination"
            ),
            SellAd(
                contractId = "planetId/345",
                expiryTime = 3L,
                timeRemaining = "3",
                creatorCompany = "company",
                price = "12.3",
                currency = "$",
                material = "material",
                quantity = "10"
            )
        )
    )
}
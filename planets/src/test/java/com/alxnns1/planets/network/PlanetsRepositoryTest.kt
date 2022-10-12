package com.alxnns1.planets.network

import com.alxnns1.utils.ResultOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetsRepositoryTest {

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
    fun `getPlanets on success returns list of planet names`() = runTest {
        val planetsResponse = Response.success(
            listOf(PlanetResponse("planetId", "planetName"))
        )
        val planetsService = mockk<PlanetsService> {
            coEvery { getPlanets() } returns planetsResponse
        }

        val subject = PlanetsRepository(planetsService)

        val actual = subject.getPlanets("")
        advanceUntilIdle()

        val expected = ResultOf.Success(listOf("planetName"))

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getPlanets with query on success returns filtered list of planet names`() = runTest {
        val planetsResponse = Response.success(
            listOf(
                PlanetResponse("planetId", "abc"),
                PlanetResponse("planetId", "bcd"),
                PlanetResponse("planetId", "cde"),
                PlanetResponse("planetId", "def"),
                PlanetResponse("planetId", "efg")
            )
        )
        val planetsService = mockk<PlanetsService> {
            coEvery { getPlanets() } returns planetsResponse
        }

        val subject = PlanetsRepository(planetsService)

        val actual = subject.getPlanets("c")
        advanceUntilIdle()

        val expected = ResultOf.Success(
            listOf(
                "abc",
                "bcd",
                "cde"
            )
        )

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `getPlanets on error returns error`() = runTest {
        val planetsResponse = Response.error<List<PlanetResponse>>(400, mockk(relaxed = true))
        val planetsService = mockk<PlanetsService> {
            coEvery { getPlanets() } returns planetsResponse
        }

        val subject = PlanetsRepository(planetsService)

        val actual = subject.getPlanets("")
        advanceUntilIdle()

        val expected = ResultOf.Error()

        Assert.assertEquals(expected, actual)
    }
}
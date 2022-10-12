package com.alxnns1.planets

import com.alxnns1.planets.network.PlanetsRepository
import com.alxnns1.utils.ResultOf
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlanetsViewModelTest {

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
    fun `getPlanets calls API and emits result`() = runTest {
        val planetResult = ResultOf.Success(listOf("planet"))
        val planetsRepository = mockk<PlanetsRepository> {
            coEvery { getPlanets("") } returns planetResult
        }

        val subject = PlanetsViewModel(planetsRepository)

        subject.getPlanets()
        advanceUntilIdle()

        coVerify { planetsRepository.getPlanets("") }
        assertEquals(planetResult, subject.planetsState.value)
    }
}
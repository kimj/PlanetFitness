package com.mentalmachines.planetfitness.features.homepage

import app.cash.turbine.test
import com.mentalmachines.planetfitness.data.Program
import com.mentalmachines.planetfitness.data.repository.ProgramRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val repository = mockk<ProgramRepository>()
    private val testDispatcher = StandardTestDispatcher()
    private val programsFlow = MutableStateFlow<List<Program>>(emptyList())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { repository.programs } returns programsFlow
        coEvery { repository.refreshPrograms() } returns Unit
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_reflectsRepositoryPrograms() = runTest {
        val viewModel = HomeViewModel(repository)
        
        viewModel.uiState.test {
            // Initial state is Loading since flow is empty
            assertTrue(awaitItem() is HomeUiState.Loading)
            
            val programs = listOf(Program(programId = "1", title = "Test Program"))
            programsFlow.value = programs
            
            val successState = awaitItem() as HomeUiState.Success
            assertEquals(programs, successState.programs)
        }
    }

    @Test
    fun refresh_callsRepository() = runTest {
        val viewModel = HomeViewModel(repository)
        viewModel.refresh()
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify { repository.refreshPrograms() }
    }
}

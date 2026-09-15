package com.mentalmachines.planetfitness.features.workoutdetail

import app.cash.turbine.test
import com.mentalmachines.planetfitness.data.Program
import com.mentalmachines.planetfitness.data.repository.ProgramRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WorkoutDetailViewModelTest {

    private val repository = mockk<ProgramRepository>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun uiState_success_whenProgramExists() = runTest {
        val program = Program(programId = "p1", title = "Program 1")
        every { repository.getProgramById("p1") } returns flowOf(program)
        
        val viewModel = WorkoutDetailViewModel("p1", "w1", repository)
        
        viewModel.uiState.test {
            val state = awaitItem() as WorkoutDetailUiState.Success
            assertEquals(program, state.program)
        }
    }

    @Test
    fun uiState_error_whenProgramNotFound() = runTest {
        every { repository.getProgramById("p1") } returns flowOf(null)
        
        val viewModel = WorkoutDetailViewModel("p1", "w1", repository)
        
        viewModel.uiState.test {
            val state = awaitItem() as WorkoutDetailUiState.Error
            assertEquals("Workout not found", state.message)
        }
    }
}

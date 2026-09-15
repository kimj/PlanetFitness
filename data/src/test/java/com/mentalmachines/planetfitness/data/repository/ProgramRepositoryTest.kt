package com.mentalmachines.planetfitness.data.repository

import app.cash.turbine.test
import com.mentalmachines.planetfitness.data.Program
import com.mentalmachines.planetfitness.data.database.ProgramDao
import com.mentalmachines.planetfitness.data.database.ProgramEntity
import com.mentalmachines.planetfitness.data.network.PlanetFitnessApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgramRepositoryTest {

    private val api = mockk<PlanetFitnessApi>()
    private val dao = mockk<ProgramDao>()

    @Test
    fun getPrograms_emitsDomainModels() = runTest {
        val entities = listOf(
            ProgramEntity(
                programId = "p1",
                title = "Program 1",
                summary = null,
                level = null,
                focus = null,
                equipment = null,
                totalWorkouts = null,
                workouts = null,
                trainer = null,
                cta = null
            )
        )
        every { dao.getAllPrograms() } returns flowOf(entities)
        val repository = ProgramRepository(api, dao)

        repository.programs.test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("p1", list[0].programId)
            awaitComplete()
        }
    }

    @Test
    fun refreshPrograms_callsApiAndInsertsToDao() = runTest {
        every { dao.getAllPrograms() } returns flowOf(emptyList())
        val repository = ProgramRepository(api, dao)
        
        val programs = listOf(
            Program(programId = "p1", title = "Program 1")
        )
        coEvery { api.getPrograms() } returns programs
        coEvery { dao.insertPrograms(any()) } returns Unit

        repository.refreshPrograms()

        coVerify { api.getPrograms() }
        coVerify { dao.insertPrograms(any()) }
    }
}

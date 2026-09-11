package com.mentalmachines.planetfitness.data.repository

import com.mentalmachines.planetfitness.data.Program
import com.mentalmachines.planetfitness.data.database.ProgramDao
import com.mentalmachines.planetfitness.data.database.toDomain
import com.mentalmachines.planetfitness.data.database.toEntity
import com.mentalmachines.planetfitness.data.network.PlanetFitnessApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ProgramRepository(
    private val api: PlanetFitnessApi,
    private val dao: ProgramDao
) {
    /**
     * A stream of programs from the local database.
     */
    val programs: Flow<List<Program>> = dao.getAllPrograms().map { entities ->
        entities.map { it.toDomain() }
    }

    /**
     * Get a specific program by ID.
     */
    fun getProgramById(programId: String): Flow<Program?> = dao.getProgramById(programId).map { entity ->
        entity?.toDomain()
    }

    /**
     * Get a specific workout by ID from a program.
     */
    fun getWorkoutById(programId: String, workoutId: String): Flow<com.mentalmachines.planetfitness.data.Workouts?> =
        getProgramById(programId).map { program ->
            program?.workouts?.find { it.workoutId == workoutId }
        }

    /**
     * Fetch fresh programs from the network and update the local database.
     */
    suspend fun refreshPrograms() {
        try {
            val networkPrograms = api.getPrograms()
            val entities = networkPrograms.mapNotNull { it.toEntity() }
            dao.insertPrograms(*entities.toTypedArray())
        } catch (e: Exception) {
            // Log error or handle as needed
            e.printStackTrace()
        }
    }
}

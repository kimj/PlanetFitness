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

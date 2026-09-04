package com.mentalmachines.planetfitness.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDao {
    @Query("SELECT * FROM programs")
    fun getAllPrograms(): Flow<List<ProgramEntity>>

    @Query("SELECT * FROM programs WHERE programId = :id")
    suspend fun getProgramById(id: String): ProgramEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrograms(vararg programs: ProgramEntity)

    @Delete
    suspend fun deleteProgram(program: ProgramEntity)

    @Query("DELETE FROM programs")
    suspend fun deleteAll()
}

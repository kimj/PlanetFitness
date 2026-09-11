package com.mentalmachines.planetfitness.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramDao {
    @Query("SELECT * FROM programs")
    fun getAllPrograms(): Flow<List<ProgramEntity>>

    @Query("SELECT * FROM programs WHERE programId = :programId")
    fun getProgramById(programId: String): Flow<ProgramEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrograms(vararg programs: ProgramEntity)
}

package com.mentalmachines.planetfitness.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mentalmachines.planetfitness.data.Program

@Entity(tableName = "programs")
data class ProgramEntity(
    @PrimaryKey val programId: String,
    val title: String?,
    val summary: String?,
    val level: String?,
    val focus: String?,
    val equipment: List<String>?,
    val totalWorkouts: Int?
)

fun Program.toEntity(): ProgramEntity? {
    val id = programId ?: return null
    return ProgramEntity(
        programId = id,
        title = title,
        summary = summary,
        level = level,
        focus = focus,
        equipment = equipment,
        totalWorkouts = totalWorkouts
    )
}

fun ProgramEntity.toDomain(): Program {
    return Program(
        programId = programId,
        title = title,
        summary = summary,
        level = level,
        focus = focus,
        equipment = equipment,
        totalWorkouts = totalWorkouts
    )
}

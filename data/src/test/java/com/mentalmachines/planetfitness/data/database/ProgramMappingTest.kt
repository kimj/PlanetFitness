package com.mentalmachines.planetfitness.data.database

import com.mentalmachines.planetfitness.data.Cta
import com.mentalmachines.planetfitness.data.Program
import com.mentalmachines.planetfitness.data.Trainer
import com.mentalmachines.planetfitness.data.Workouts
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgramMappingTest {

    @Test
    fun toEntityMapping() {
        val domain = Program(
            programId = "p1",
            title = "Title",
            trainer = Trainer(name = "Trainer"),
            summary = "Summary",
            level = "Intermediate",
            focus = "Full Body",
            equipment = listOf("Mat"),
            totalWorkouts = 10,
            workouts = listOf(Workouts(workoutId = "w1")),
            cta = Cta(label = "Go")
        )

        val entity = domain.toEntity()

        assertEquals("p1", entity?.programId)
        assertEquals("Title", entity?.title)
        assertEquals("Trainer", entity?.trainer?.name)
        assertEquals("Summary", entity?.summary)
        assertEquals("Intermediate", entity?.level)
        assertEquals("Full Body", entity?.focus)
        assertEquals(listOf("Mat"), entity?.equipment)
        assertEquals(10, entity?.totalWorkouts)
        assertEquals(1, entity?.workouts?.size)
        assertEquals("Go", entity?.cta?.label)
    }

    @Test
    fun toDomainMapping() {
        val entity = ProgramEntity(
            programId = "p1",
            title = "Title",
            summary = "Summary",
            level = "Intermediate",
            focus = "Full Body",
            equipment = listOf("Mat"),
            totalWorkouts = 10,
            workouts = listOf(Workouts(workoutId = "w1")),
            trainer = Trainer(name = "Trainer"),
            cta = Cta(label = "Go")
        )

        val domain = entity.toDomain()

        assertEquals("p1", domain.programId)
        assertEquals("Title", domain.title)
        assertEquals("Trainer", domain.trainer?.name)
        assertEquals("Summary", domain.summary)
        assertEquals("Intermediate", domain.level)
        assertEquals("Full Body", domain.focus)
        assertEquals(listOf("Mat"), domain.equipment)
        assertEquals(10, domain.totalWorkouts)
        assertEquals(1, domain.workouts?.size)
        assertEquals("Go", domain.cta?.label)
    }
}

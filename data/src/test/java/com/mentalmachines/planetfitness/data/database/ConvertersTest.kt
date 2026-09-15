package com.mentalmachines.planetfitness.data.database

import com.mentalmachines.planetfitness.data.Cta
import com.mentalmachines.planetfitness.data.Trainer
import com.mentalmachines.planetfitness.data.Workouts
import org.junit.Assert.assertEquals
import org.junit.Test

class ConvertersTest {
    private val converters = Converters()

    @Test
    fun stringListConversion() {
        val list = listOf("Equipment 1", "Equipment 2")
        val json = converters.fromStringList(list)
        val result = converters.toStringList(json)
        assertEquals(list, result)
    }

    @Test
    fun workoutsListConversion() {
        val workouts = listOf(
            Workouts(workoutId = "w1", title = "Workout 1"),
            Workouts(workoutId = "w2", title = "Workout 2")
        )
        val json = converters.fromWorkoutsList(workouts)
        val result = converters.toWorkoutsList(json)
        assertEquals(workouts, result)
    }

    @Test
    fun trainerConversion() {
        val trainer = Trainer(name = "Trainer Name")
        val json = converters.fromTrainer(trainer)
        val result = converters.toTrainer(json)
        assertEquals(trainer, result)
    }

    @Test
    fun ctaConversion() {
        val cta = Cta(label = "Action", action = "URL")
        val json = converters.fromCta(cta)
        val result = converters.toCta(json)
        assertEquals(cta, result)
    }
}

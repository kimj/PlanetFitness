package com.mentalmachines.planetfitness.data


data class Cta (

    val label: String? = null,
    val action: String? = null
)

data class Program (

    val programId: String? = null,
    val title: String? = null,
    val trainer: Trainer? = null,
    val summary: String? = null,
    val level: String? = null,
    val focus: String? = null,
    val equipment: List<String>? = null,
    val totalWorkouts: Int? = null,
    val workouts: List<Workouts>? = null,
    val cta: Cta? = null
)
data class Trainer (

    val name: String? = null
)

data class Workouts (

    val workoutId: String? = null,
    val title: String? = null,
    val durationMinutes: Int? = null,
    val level: String? = null,
    val order: Int? = null,
    val description: String? = null
)
package com.mentalmachines.planetfitness.features.workoutdetail

import com.mentalmachines.planetfitness.data.Workouts

sealed interface WorkoutDetailUiState {
    object Loading : WorkoutDetailUiState
    data class Success(val workout: Workouts) : WorkoutDetailUiState
    data class Error(val message: String) : WorkoutDetailUiState
}

package com.mentalmachines.planetfitness.features.workoutdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mentalmachines.planetfitness.data.repository.ProgramRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class WorkoutDetailViewModel(
    programId: String,
    workoutId: String,
    repository: ProgramRepository
) : ViewModel() {

    val uiState: StateFlow<WorkoutDetailUiState> = repository.getWorkoutById(programId, workoutId)
        .map { workout ->
            if (workout != null) {
                WorkoutDetailUiState.Success(workout)
            } else {
                WorkoutDetailUiState.Error("Workout not found")
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = WorkoutDetailUiState.Loading
        )
}

class WorkoutDetailViewModelFactory(
    private val programId: String,
    private val workoutId: String,
    private val repository: ProgramRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkoutDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkoutDetailViewModel(programId, workoutId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

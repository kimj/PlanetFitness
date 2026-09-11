package com.mentalmachines.planetfitness.features.programoverview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mentalmachines.planetfitness.data.repository.ProgramRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ProgramOverviewViewModel(
    private val programId: String,
    private val repository: ProgramRepository
) : ViewModel() {

    val uiState: StateFlow<ProgramOverviewUiState> = repository.getProgramById(programId)
        .map { program ->
            if (program != null) {
                ProgramOverviewUiState.Success(program)
            } else {
                ProgramOverviewUiState.Error("Program not found")
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProgramOverviewUiState.Loading
        )
}

class ProgramOverviewViewModelFactory(
    private val programId: String,
    private val repository: ProgramRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProgramOverviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProgramOverviewViewModel(programId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.mentalmachines.planetfitness.features.programoverview

import com.mentalmachines.planetfitness.data.Program

sealed interface ProgramOverviewUiState {
    object Loading : ProgramOverviewUiState
    data class Success(val program: Program) : ProgramOverviewUiState
    data class Error(val message: String) : ProgramOverviewUiState
}

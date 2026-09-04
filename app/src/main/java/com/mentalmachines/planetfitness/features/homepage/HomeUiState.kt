package com.mentalmachines.planetfitness.features.homepage

import com.mentalmachines.planetfitness.data.Program

sealed interface HomeUiState {
    object Loading : HomeUiState
    data class Success(val programs: List<Program>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

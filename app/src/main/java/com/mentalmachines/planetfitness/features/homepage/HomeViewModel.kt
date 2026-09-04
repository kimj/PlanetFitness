package com.mentalmachines.planetfitness.features.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mentalmachines.planetfitness.data.repository.ProgramRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: ProgramRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = repository.programs
        .map { programs ->
            if (programs.isEmpty()) {
                HomeUiState.Loading
            } else {
                HomeUiState.Success(programs)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading
        )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            repository.refreshPrograms()
        }
    }
}

class HomeViewModelFactory(
    private val repository: ProgramRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

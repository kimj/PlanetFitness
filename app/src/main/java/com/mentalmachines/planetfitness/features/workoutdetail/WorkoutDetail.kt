package com.mentalmachines.planetfitness.features.workoutdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mentalmachines.planetfitness.data.database.AppDatabase
import com.mentalmachines.planetfitness.data.network.NetworkClient
import com.mentalmachines.planetfitness.data.repository.ProgramRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailPage(
    programId: String?,
    workoutId: String?,
    onBackClick: () -> Unit,
    viewModel: WorkoutDetailViewModel = viewModel(
        factory = WorkoutDetailViewModelFactory(
            programId ?: "",
            workoutId ?: "",
            ProgramRepository(
                NetworkClient.api,
                AppDatabase.getDatabase(LocalContext.current).programDao()
            )
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Workout Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = uiState) {
                is WorkoutDetailUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is WorkoutDetailUiState.Success -> {
                    val workout = state.workout
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(workout.title ?: "No Title", style = MaterialTheme.typography.headlineMedium)
                        Text("${workout.durationMinutes ?: 0} minutes", style = MaterialTheme.typography.bodyLarge)
                        Text(workout.level ?: "N/A", style = MaterialTheme.typography.bodyMedium)
                        Text(workout.description ?: "No Description", style = MaterialTheme.typography.bodySmall)
                    }
                }
                is WorkoutDetailUiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}


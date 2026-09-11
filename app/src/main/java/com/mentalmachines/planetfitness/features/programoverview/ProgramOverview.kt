package com.mentalmachines.planetfitness.features.programoverview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.mentalmachines.planetfitness.data.Program
import com.mentalmachines.planetfitness.data.Workouts
import com.mentalmachines.planetfitness.data.database.AppDatabase
import com.mentalmachines.planetfitness.data.network.NetworkClient
import com.mentalmachines.planetfitness.data.repository.ProgramRepository


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramOverviewScreen(
    programId: String?,
    onWorkoutClick: (String) -> Unit,
    onBackClick: () -> Unit,
    viewModel: ProgramOverviewViewModel = viewModel(
        factory = ProgramOverviewViewModelFactory(
            programId ?: "",
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
                title = { Text("Program Overview") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            when (val state = uiState) {
                is ProgramOverviewUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ProgramOverviewUiState.Success -> {
                    val program = state.program
                    Column(modifier = Modifier.padding(16.dp)) {
                        ProgramOverviewHeader(program.title, program.level, program.focus)

                        Spacer(modifier = Modifier.height(8.dp))
                        ProgramOverviewBody(program)
                        Spacer(modifier = Modifier.height(8.dp))
                        ProgramOverViewWorkoutList(program.workouts, onWorkoutClick)
                    }
                }
                is ProgramOverviewUiState.Error -> {
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

@Composable
fun ProgramOverviewHeader(title: String?, level: String?, focus: String?) {
    Column {
        Text(
            text = title ?: "No Title",
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = level ?: "N/A",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = focus ?: "N/A",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ProgramOverviewBody(program: Program) {
    Column() {
        Text(program.summary ?: "No Summary")
        Text("Read More", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun ProgramOverViewWorkoutList(workouts: List<Workouts>?, onWorkoutClick: (String) -> Unit) {
    Text("Workouts", style = MaterialTheme.typography.titleMedium)

    Column() {
        workouts?.forEach { workout ->
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clickable { workout.workoutId?.let { onWorkoutClick(it) } }
            ) {
                Text(workout.title ?: "No Title", style = MaterialTheme.typography.bodyLarge)
                Text(workout.description ?: "No Description", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
package com.mentalmachines.planetfitness.features.workoutdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mentalmachines.planetfitness.R
import com.mentalmachines.planetfitness.data.database.AppDatabase
import com.mentalmachines.planetfitness.data.network.NetworkClient
import com.mentalmachines.planetfitness.data.repository.ProgramRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutDetailPage(
    programId: String?,
    workoutId: String?,
    onBackClick: () -> Unit,
    onStartWorkoutTimerClick: (minutes : Int) -> Unit,
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
    val workoutId = workoutId ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.workout_detail_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button_content_description))
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
                    val program = state.program
                    val workout = state.program.workouts?.find { it.workoutId == workoutId }
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(workout?.title ?: stringResource(R.string.no_title), style = MaterialTheme.typography.headlineMedium)
                        Text(stringResource(R.string.workout_order_format, program.title ?: stringResource(R.string.no_title), workout?.order ?: 0), style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 4.dp))
                        Text(stringResource(R.string.trainer_prefix, program.trainer?.name ?: stringResource(R.string.no_trainer)) , style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(vertical = 4.dp))

                        Spacer(modifier = Modifier.padding(4.dp))

                        Text(stringResource(R.string.duration_minutes, workout?.durationMinutes ?: 0), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(vertical = 8.dp))
                        Text(workout?.level ?: stringResource(R.string.not_available), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 8.dp))
                        Text(program.focus ?: stringResource(R.string.not_available), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 8.dp))
                        Text(program.equipment.toString(), style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(vertical = 8.dp))
                        Text(workout?.description ?: stringResource(R.string.no_description), style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.padding(4.dp))
                        Button(content = { Text(stringResource(R.string.start_workout_button)) }, onClick = { onStartWorkoutTimerClick(
                            workout?.durationMinutes ?: 0
                        ) })
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


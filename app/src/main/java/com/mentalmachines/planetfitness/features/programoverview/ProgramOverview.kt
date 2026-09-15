package com.mentalmachines.planetfitness.features.programoverview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
                title = { Text(stringResource(R.string.program_overview_title)) },
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
            text = title ?: stringResource(R.string.no_title),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = level ?: stringResource(R.string.not_available),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = focus ?: stringResource(R.string.not_available),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ProgramOverviewBody(program: Program) {
    Column() {
        Text(program.summary ?: stringResource(R.string.no_summary))
        Text(stringResource(R.string.read_more), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        HorizontalDivider(Modifier.padding(8.dp), DividerDefaults.Thickness, DividerDefaults.color)

    }
}

@Composable
fun ProgramOverViewWorkoutList(workouts: List<Workouts>?, onWorkoutClick: (String) -> Unit) {
    Text(stringResource(R.string.workouts_count, workouts?.size ?: 0), style = MaterialTheme.typography.titleMedium)

    Column() {
        workouts?.forEach { workout ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { workout.workoutId?.let { onWorkoutClick(it) } }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(workout.title ?: stringResource(R.string.no_title), style = MaterialTheme.typography.bodyLarge)
                    Text(workout.description ?: stringResource(R.string.no_description), style = MaterialTheme.typography.bodySmall)
                }
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            HorizontalDivider(thickness = 0.5.dp, color = DividerDefaults.color)
        }
    }
}
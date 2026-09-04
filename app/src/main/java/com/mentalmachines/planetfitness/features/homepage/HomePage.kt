package com.mentalmachines.planetfitness.features.homepage

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mentalmachines.planetfitness.R
import com.mentalmachines.planetfitness.data.Program
import com.mentalmachines.planetfitness.data.database.AppDatabase
import com.mentalmachines.planetfitness.data.network.NetworkClient
import com.mentalmachines.planetfitness.data.repository.ProgramRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            ProgramRepository(
                NetworkClient.api,
                AppDatabase.getDatabase(LocalContext.current).programDao()
            )
        )
    )
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    val items = listOf(
        NavigationItem(stringResource(R.string.bottom_navigation_home) , Icons.Filled.Home),
        NavigationItem(stringResource(R.string.bottom_navigation_workouts), Icons.Filled.Search),
        NavigationItem(stringResource(R.string.bottom_navigation_my_journey), Icons.Filled.Person)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = items,
                selectedItem = selectedItem,
                onItemSelected = { selectedItem = it }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedItem) {
                0 -> HomeContent(uiState)
                1 -> WorkoutsContent()
                2 -> MyJourneyContent()
            }
        }
    }
}

@Composable
fun HomeContent(uiState: HomeUiState) {
    when (uiState) {
        is HomeUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is HomeUiState.Success -> {
            ProgramList(programs = uiState.programs)
        }
        is HomeUiState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = uiState.message, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
fun ProgramList(programs: List<Program>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(programs) { program ->
            ProgramCard(program = program)
        }
    }
}

@Composable
fun ProgramCard(program: Program) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = program.title ?: "No Title",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = program.summary ?: "",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )
            Text(
                text = "Level: ${program.level ?: "N/A"}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
@Composable
fun WorkoutsContent(){
    Text(text = "Workouts Content")
}

@Composable
fun MyJourneyContent(){
    Text(text = "My Journey Content")
}

@Composable
fun BottomNavigationBar(
    items: List<NavigationItem>,
    selectedItem: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}

data class NavigationItem(
    val title: String,
    val icon: ImageVector
)

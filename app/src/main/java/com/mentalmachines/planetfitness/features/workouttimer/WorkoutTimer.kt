package com.mentalmachines.planetfitness.features.workouttimer

import android.annotation.SuppressLint
import android.os.SystemClock
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WorkoutTimerScreen(minutes: Int, onBackClick: () -> Unit) {
    var startTime by remember { mutableLongStateOf(0L) }
    var isRunning by remember { mutableStateOf(true) }
    var elapsed by remember { mutableLongStateOf(0L) }

    val totalMs = remember(minutes) { minutes.toLong() * 60 * 1000 }

    LaunchedEffect(isRunning) {
        if (isRunning) {
            startTime = SystemClock.uptimeMillis()
            while (isRunning) {
                elapsed = SystemClock.uptimeMillis() - startTime
                if (elapsed >= totalMs) {
                    isRunning = false
                }
                delay(100L) // Update frequently for smooth UI
            }
        }
    }

    val remaining = (totalMs - elapsed).coerceAtLeast(0L)

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Workout Timer") },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
    }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize(), contentAlignment = Alignment.Center) {
            Column {
                Text(text = formatMs(remaining), style = MaterialTheme.typography.displayLarge)

                Text(text = "You Can Do IT!", style = MaterialTheme.typography.bodyLarge)
                Row {
                    Button(content = { Text("Pause") }, onClick = { isRunning = false }, modifier = Modifier.padding(8.dp))
                    Button(content = { Text("Restart") }, onClick = { isRunning = true }, modifier = Modifier.padding(8.dp))
                }
            }
        }
    }
}

fun formatMs(ms: Long): String {
    val seconds = ms / 1000
    val minutes = seconds / 60
    val secs = seconds % 60
    return String.format("%02d:%02d", minutes, secs)
}



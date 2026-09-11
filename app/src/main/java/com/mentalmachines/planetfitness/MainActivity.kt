package com.mentalmachines.planetfitness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.mentalmachines.planetfitness.navigation.AppNavHost
import com.mentalmachines.planetfitness.ui.theme.PlanetFitnessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PlanetFitnessTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

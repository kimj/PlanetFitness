package com.mentalmachines.planetfitness.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mentalmachines.planetfitness.features.homepage.HomePage
import com.mentalmachines.planetfitness.features.programoverview.ProgramOverviewScreen
import com.mentalmachines.planetfitness.features.workoutdetail.WorkoutDetailPage
import com.mentalmachines.planetfitness.features.workouttimer.WorkoutTimerScreen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object ProgramOverview : Screen("program_overview/{programId}") {
        fun createRoute(programId: String) = "program_overview/$programId"
    }
    object WorkoutDetail : Screen("workout_detail/{programId}/{workoutId}") {
        fun createRoute(programId: String, workoutId: String) = "workout_detail/$programId/$workoutId"
    }
    object WorkoutTimer : Screen("workout_timer/{minutes}") {
        fun createRoute(minutes: Int) = "workout_timer/$minutes"
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomePage(
                onProgramClick = { programId ->
                    navController.navigate(Screen.ProgramOverview.createRoute(programId))
                }
            )
        }
        composable(
            route = Screen.ProgramOverview.route,
            arguments = listOf(navArgument("programId") { type = NavType.StringType })
        ) { backStackEntry ->
            val programId = backStackEntry.arguments?.getString("programId")
            ProgramOverviewScreen(
                programId = programId,
                onWorkoutClick = { workoutId ->
                    programId?.let {
                        navController.navigate(Screen.WorkoutDetail.createRoute(it, workoutId))
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            route = Screen.WorkoutDetail.route,
            arguments = listOf(
                navArgument("programId") { type = NavType.StringType },
                navArgument("workoutId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val programId = backStackEntry.arguments?.getString("programId")
            val workoutId = backStackEntry.arguments?.getString("workoutId") ?: ""


            WorkoutDetailPage(
                programId = programId,
                workoutId = workoutId,
                onBackClick = { navController.popBackStack() },
                onStartWorkoutTimerClick = { minutes ->
                    navController.navigate(Screen.WorkoutTimer.createRoute(minutes))
                },
            )
        }
        composable(
            route = Screen.WorkoutTimer.route,
            arguments = listOf(
                navArgument("minutes") { type = NavType.IntType },
            )
        ) { backStackEntry ->
            WorkoutTimerScreen(
                minutes = backStackEntry.arguments?.getInt("minutes") ?: 0,
                onBackClick = { navController.popBackStack() }
            )
        }

    }
}

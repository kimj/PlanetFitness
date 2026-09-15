package com.mentalmachines.planetfitness

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.mentalmachines.planetfitness.navigation.AppNavHost
import com.mentalmachines.planetfitness.navigation.Screen
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var navController: TestNavHostController

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }

        assertEquals(Screen.Home.route, navController.currentBackStackEntry?.destination?.route)
    }

    @Test
    fun appNavHost_clickProgram_navigatesToOverview() {

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }

        assertEquals(Screen.Home.route, navController.currentBackStackEntry?.destination?.route)
    }
}

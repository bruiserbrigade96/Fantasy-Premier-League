package com.example.fplassistant.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.ViewModelProvider
import com.example.fplassistant.ui.screens.dashboard.DashboardScreen
import com.example.fplassistant.ui.screens.players.PlayersScreen

object Routes {
    const val DASHBOARD = "dashboard"
    const val PLAYERS = "players"
}

@Composable
fun AppNavHost(
    viewModelFactory: ViewModelProvider.Factory
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.DASHBOARD) {
        composable(Routes.DASHBOARD) {
            DashboardScreen(
                viewModelFactory = viewModelFactory,
                onBrowsePlayers = { navController.navigate(Routes.PLAYERS) }
            )
        }
        composable(Routes.PLAYERS) {
            PlayersScreen(
                viewModelFactory = viewModelFactory,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
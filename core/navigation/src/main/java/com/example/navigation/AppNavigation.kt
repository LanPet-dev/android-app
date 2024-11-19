package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.AuthNavigation
import com.example.auth.navigation.authNavGraph
import com.example.landing.navigation.LandingNavigation
import com.example.landing.navigation.landingNavGraph


@Composable
fun AppNavigation(
    navigationManager: NavigationManager,
) {
    val navController = rememberNavController()

    LaunchedEffect(navigationManager) {
        navigationManager.commands.collect { command ->
            println("command :: $command")
            navController.navigate(command.route)
        }
    }

    NavHost(
        navController = navController,
        startDestination = LandingNavigation.Route.LANDING
    ) {
        landingNavGraph {
            println("navigate to login screen")
            navigationManager.navigate(object : NavigationCommand {
                override val route: String
                    get() = AuthNavigation.Route.LOGIN
            }
            )
        }
        authNavGraph()
    }
}
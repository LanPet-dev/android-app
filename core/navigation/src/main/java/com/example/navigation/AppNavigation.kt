package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.authNavGraph
import com.example.auth.navigation.navigateToLoginScreen
import com.example.landing.navigation.Landing
import com.example.landing.navigation.landingNavGraph


@Composable
fun AppNavigation(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Landing
    ) {
        landingNavGraph {
            println("navigate to login screen")
            navController.navigateToLoginScreen()
        }
        authNavGraph()
    }
}
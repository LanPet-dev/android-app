package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.authNavGraph
import com.example.auth.navigation.navigateToLoginScreen
import com.example.landing.navigation.Landing
import com.example.landing.navigation.landingNavGraph
import com.example.profile.navigation.ProfileCreateHasPet
import com.example.profile.navigation.navigateToProfileCreateHasPet
import com.example.profile.navigation.navigateToProfileCreateNoPet
import com.example.profile.navigation.navigateToProfileCreateYesPet
import com.example.profile.navigation.profileNavGraph


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
//            navController.navigateToLoginScreen()
            navController.navigateToProfileCreateHasPet()
        }
        authNavGraph()
        profileNavGraph(
            onNavigateToYesPetScreen = {
                navController.navigateToProfileCreateYesPet()
            },
            onNavigateToNoPetScreen = {
                navController.navigateToProfileCreateNoPet()
            },
        )
    }
}
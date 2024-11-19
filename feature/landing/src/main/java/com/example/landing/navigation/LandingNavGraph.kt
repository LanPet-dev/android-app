package com.example.landing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.landing.LandingScreen
import kotlinx.serialization.Serializable


fun NavGraphBuilder.landingNavGraph(
    onNavigateToLogin: () -> Unit,
) {
    composable<Landing> {
        LandingScreen(
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

fun NavController.navigateToLandingScreen() {
    navigate(Landing, navOptions {
        popUpToId
    })
}

@Serializable
object Landing


package com.lanpet.feature.landing.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.lanpet.feature.landing.LandingScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.landingNavGraph(onNavigateToLogin: () -> Unit) {
    composable<Landing> {
        LandingScreen(
            onNavigateToLogin = onNavigateToLogin,
        )
    }
}

fun NavController.navigateToLandingScreen() {
    navigate(
        Landing,
        navOptions {
            popUpToId
        },
    )
}

@Serializable
object Landing

package com.example.landing.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.landing.LandingScreen


fun NavGraphBuilder.landingNavGraph(
    onNavigateToLogin: () -> Unit,
) {
    composable(route = LandingNavigation.Route.LANDING) {
        LandingScreen(
            onNavigateToLogin = onNavigateToLogin
        )
    }
}

object LandingNavigation {
    object Route {
        const val LANDING = "landing"
    }
}

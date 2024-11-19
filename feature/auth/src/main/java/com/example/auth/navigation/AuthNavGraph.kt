package com.example.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.auth.LoginScreen

fun NavGraphBuilder.authNavGraph(
) {
    composable(route = AuthNavigation.Route.LOGIN) {
        LoginScreen(
        )
    }
}

object AuthNavigation {
    object Route {
        const val LOGIN = "login"
    }
}

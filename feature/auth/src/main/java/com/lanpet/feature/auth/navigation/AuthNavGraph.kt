package com.lanpet.feature.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.lanpet.feature.auth.LoginScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.authNavGraph() {
    composable<Login> {
        LoginScreen()
    }
}

fun NavController.navigateToLoginScreen() {
    this.navigate(
        Login,
        navOptions {
            popUpTo(0) {
                // 0은 시작 destination 을 의미
                inclusive = true // true: 시작 destination 도 제거, false: 시작 destination 유지
            }
            launchSingleTop = true // 중복 destination 방지
            restoreState = false
        },
    )
}

@Serializable
object Login

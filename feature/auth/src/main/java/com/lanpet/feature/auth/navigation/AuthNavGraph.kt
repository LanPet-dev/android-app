package com.lanpet.feature.auth.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.lanpet.feature.auth.LoginScreen
import kotlinx.serialization.Serializable
import timber.log.Timber

fun NavGraphBuilder.authNavGraph() {
    composable<Login> {
        LoginScreen()
    }
}

@SuppressLint("RestrictedApi")
fun NavController.navigateToLoginScreen() {
    val startDestinationId = this.currentBackStack.value.first().destination.id

    navigate(
        Login,
        navOptions =
            navOptions {
                popUpTo(startDestinationId) {
                    inclusive = true
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            },
    )
}

@Serializable
data object Login

package com.lanpet.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lanpet.feature.settings.SettingsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.settingsNavGraph(
    onNavigateUp: () -> Unit
) {
    composable<Settings> {
        SettingsScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToSettings() {
    navigate(
        Settings,
    ) {
        launchSingleTop = true
    }
}


@Serializable
data object Settings

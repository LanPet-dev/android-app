package com.lanpet.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.lanpet.feature.settings.LogoutDialog
import com.lanpet.feature.settings.MemberLeaveCompleteScreen
import com.lanpet.feature.settings.SettingsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.settingsNavGraph(
    onNavigateUp: () -> Unit,
    onNavigateToMemberLeave: () -> Unit,
    onOpenLogoutDialog: () -> Unit,
    onDismissLogoutDialog: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToMemberLeaveComplete: () -> Unit,
) {
    composable<Settings> {
        SettingsScreen(
            onNavigateUp = onNavigateUp,
            onOpenLogoutDialog = onOpenLogoutDialog,
            onNavigateToMemberLeave = onNavigateToMemberLeave,
        )
    }
    dialog<LogoutDialog> {
        LogoutDialog(
            onDismiss = onDismissLogoutDialog,
            onLogout = onLogout,
        )
    }
    composable<MemberLeave> {
        com.lanpet.feature.settings.MemberLeaveScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToMemberLeaveComplete = onNavigateToMemberLeaveComplete,
        )
    }
    composable<MemberLeaveComplete> {
        MemberLeaveCompleteScreen(
            onLogout = onLogout,
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

fun NavController.navigateToLogoutDialog() {
    navigate(
        LogoutDialog,
    ) {
        launchSingleTop = true
    }
}

fun NavController.navigateToMemberLeave() {
    navigate(
        MemberLeave,
    ) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToMemberLeaveComplete() {
    navigate(
        MemberLeaveComplete,
    ) {
        launchSingleTop = true
    }
}

@Serializable
data object Settings

@Serializable
data object LogoutDialog

@Serializable
object MemberLeave

@Serializable
object MemberLeaveComplete

package com.example.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.profile.screen.ProfileCreateHasPetScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.profileNavGraph(
    onNavigateToYesPetScreen: () -> Unit,
    onNavigateToNoPetScreen: () -> Unit,
) {
    composable<ProfileCreateHasPet>() {
        ProfileCreateHasPetScreen(
            onNavigateToYesPetScreen = onNavigateToYesPetScreen,
            onNavigateToNoPetScreen = onNavigateToNoPetScreen,
        )
    }
}

fun NavController.navigateToProfileCreateHasPet() {
    this.navigate(ProfileCreateHasPet)
}

fun NavController.navigateToProfileCreateYesPet() {
    this.navigate(ProfileCreateHasPet)
}

fun NavController.navigateToProfileCreateNoPet() {
    this.navigate(ProfileCreateHasPet)
}

@Serializable
object ProfileCreateHasPet

@Serializable
object ProfileCreateNoPet

@Serializable
object ProfileCreateYesPet

package com.example.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.profile.screen.ProfileCreateHasPetScreen
import com.example.profile.screen.yespet.ProfileCreateYesPetNameScreen
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
    composable<ProfileCreateYesPet>() {
        ProfileCreateYesPetNameScreen()
    }
}

fun NavController.navigateToProfileCreateHasPet() {
    this.navigate(ProfileCreateHasPet)
}

fun NavController.navigateToProfileCreateYesPet() {
    this.navigate(ProfileCreateYesPet)
}

fun NavController.navigateToProfileCreateNoPet() {
    this.navigate(ProfileCreateYesPet)
}

@Serializable
object ProfileCreateHasPet

@Serializable
object ProfileCreateNoPet

@Serializable
object ProfileCreateYesPet

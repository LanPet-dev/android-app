package com.example.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.profile.screen.ProfileCreateDoneScreen
import com.example.profile.screen.ProfileCreateHasPetScreen
import com.example.profile.screen.nopet.ProfileCreateNoPetIntroScreen
import com.example.profile.screen.nopet.ProfileCreateNoPetNameScreen
import com.example.profile.screen.yespet.ProfileCreateYesPetIntroScreen
import com.example.profile.screen.yespet.ProfileCreateYesPetNameScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.profileNavGraph(
    onNavigateToYesPetNameScreen: () -> Unit,
    onNavigateToNoPetNameScreen: () -> Unit,
    onNavigateToYesPetIntroScreen: () -> Unit,
    onNavigateToNoPetIntroScreen: () -> Unit,
) {
    composable<ProfileCreateHasPet> {
        ProfileCreateHasPetScreen(
            onNavigateToYesPetScreen = onNavigateToYesPetIntroScreen,
            onNavigateToNoPetScreen = onNavigateToNoPetIntroScreen,
        )
    }
    composable<ProfileCreateYesPetName> {
        ProfileCreateYesPetNameScreen()
    }
    composable<ProfileCreateNoPetName> {
        ProfileCreateNoPetNameScreen()
    }
    composable<ProfileIntroNoPet> {
        ProfileCreateNoPetIntroScreen(
            onNavigateToNoPetNameScreen = onNavigateToNoPetNameScreen,
        )
    }
    composable<ProfileIntroYesPet> {
        ProfileCreateYesPetIntroScreen(
            onNavigateToYesPetNameScreen = onNavigateToYesPetNameScreen,
        )
    }
    composable<ProfileCreateDone> {
        ProfileCreateDoneScreen()
    }
}

fun NavController.navigateToProfileCreateHasPet() {
    this.navigate(ProfileCreateHasPet)
}

fun NavController.navigateToProfileCreateYesPetName() {
    this.navigate(ProfileCreateYesPetName)
}

fun NavController.navigateToProfileCreateNoPetName() {
    this.navigate(ProfileCreateNoPetName)
}

fun NavController.navigateToProfileIntroYesPet() {
    this.navigate(ProfileIntroYesPet)
}

fun NavController.navigateToProfileIntroNoPet() {
    this.navigate(ProfileIntroNoPet)
}

fun NavController.navigateToProfileCreateDone() {
    this.navigate(ProfileCreateDone)
}

fun NavController.navigateToProfileCreateHumanBio() {
    this.navigate(ProfileCreateHumanBio)
}

fun NavController.navigateToProfileCreatePetBio() {
    this.navigate(ProfileCreatePetBio)
}

fun NavController.navigateToProfileCreatePetCategory() {
    this.navigate(ProfileCreatePetCategory)
}

fun NavController.navigateToProfileCreatePetSpecies() {
    this.navigate(ProfileCreatePetSpecies)
}

fun NavController.navigateToProfileCreateHumanAge() {
    this.navigate(ProfileCreateHumanAge)
}

@Serializable
object ProfileCreateHasPet

@Serializable
object ProfileCreateNoPetName

@Serializable
object ProfileCreateYesPetName

@Serializable
object ProfileIntroYesPet

@Serializable
object ProfileIntroNoPet

@Serializable
object ProfileCreateDone

@Serializable
object ProfileCreateHumanBio

@Serializable
object ProfileCreatePetBio

@Serializable
object ProfileCreatePetCategory

@Serializable
object ProfileCreatePetSpecies

@Serializable
object ProfileCreateHumanAge

package com.example.profile.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
    composable<ProfileCreateHasPet>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(350)
            )
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(350)
            )
        }
    ) {
        ProfileCreateHasPetScreen(
            onNavigateToYesPetScreen = onNavigateToYesPetIntroScreen,
            onNavigateToNoPetScreen = onNavigateToNoPetIntroScreen,
        )
    }
    composable<ProfileCreateYesPetName>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(350)
            )
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(350)
            )
        }
    ) {
        ProfileCreateYesPetNameScreen()
    }
    composable<ProfileCreateNoPetName>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(350)
            )
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(350)
            )
        }
    ) {
        ProfileCreateNoPetNameScreen()
    }
    composable<ProfileIntroNoPet>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(350)
            )
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(350)
            )
        }
    ) {
        ProfileCreateNoPetIntroScreen(
            onNavigateToNoPetNameScreen = onNavigateToNoPetNameScreen
        )
    }
    composable<ProfileIntroYesPet>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(350)
            )
        },
        exitTransition = {
            fadeOut()
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(350)
            )
        }
    ) {
        ProfileCreateYesPetIntroScreen(
            onNavigateToYesPetNameScreen = onNavigateToYesPetNameScreen
        )
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

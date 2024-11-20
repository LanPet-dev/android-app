package com.example.profile.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
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
            onNavigateToYesPetScreen = onNavigateToYesPetScreen,
            onNavigateToNoPetScreen = onNavigateToNoPetScreen,
        )
    }
    composable<ProfileCreateYesPet>(
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

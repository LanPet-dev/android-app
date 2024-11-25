package com.lanpet.free.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.free.FreeBoardScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.freeNavGraph() {
    navigation<FreeBoardBaseRoute>(
        startDestination = FreeBoard,
    ) {
        composable<FreeBoard>() {
            FreeBoardScreen()
        }
    }
}

fun NavController.navigateToFreeBoardScreen() {
    navigate(
        FreeBoard,
    ) {
        popUpTo(0) {
            inclusive = true
        }
    }
}

@Serializable
object FreeBoardBaseRoute

@Serializable
object FreeBoard
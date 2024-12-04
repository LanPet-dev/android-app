package com.lanpet.free.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.lanpet.free.FreeBoardDetailScreen
import com.lanpet.free.FreeBoardScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.freeNavGraph(
    onNavigateUp: () -> Unit,
) {
    navigation<FreeBoardBaseRoute>(
        startDestination = FreeBoard,
    ) {
        composable<FreeBoard>() {
            FreeBoardScreen()
        }
        composable<FreeBoardDetail> {
            it.toRoute<FreeBoardDetail>().postId.let { postId ->
                FreeBoardDetailScreen(
                    postId = postId.toInt(),
                    onNavigateUp = onNavigateUp
                )
            }
        }
    }
}

fun NavController.navigateToFreeBoardBaseRoute() {
    navigate(
        FreeBoardBaseRoute,
    ) {
        launchSingleTop = true
        popUpTo(0) {
            inclusive = true
        }
    }
}

fun NavController.navigateToFreeBoardDetailScreen(postId: String) {
    navigate(
        FreeBoardDetail(postId = postId),
    ) {
    }
}

fun NavController.navigateToFreeBoardScreen() {
    navigate(
        FreeBoard,
    ) {
        launchSingleTop = true
//        popUpTo(0) {
//            inclusive = true
//        }
    }
}

@Serializable
object FreeBoardBaseRoute

@Serializable
data class FreeBoardDetail(
    val postId: String,
)

@Serializable
data object FreeBoard {
    override fun toString(): String {
        return (this::class.java.toString()).split(" ").last()
    }
}
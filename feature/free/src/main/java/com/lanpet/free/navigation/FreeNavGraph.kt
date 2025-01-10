package com.lanpet.free.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.lanpet.free.screen.FreeBoardDetailScreen
import com.lanpet.free.screen.FreeBoardScreen
import com.lanpet.free.screen.FreeBoardWriteScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.freeNavGraph(
    onNavigateUp: () -> Unit,
    onNavigateToFreeBoardWriteFreeBoard: () -> Unit,
    onNavigateToFreeBoardDetail: (postId: String) -> Unit,
) {
    navigation<FreeBoardBaseRoute>(
        startDestination = FreeBoard,
    ) {
        composable<FreeBoard> {
            FreeBoardScreen(
                onNavigateUp = onNavigateUp,
                onNavigateToFreeBoardWrite = onNavigateToFreeBoardWriteFreeBoard,
                onNavigateToFreeBoardDetail = onNavigateToFreeBoardDetail,
            )
        }
        composable<FreeBoardDetail> {
            it.toRoute<FreeBoardDetail>().postId.let { postId ->
                FreeBoardDetailScreen(
                    postId = postId,
                    onNavigateUp = onNavigateUp,
                )
            }
        }
        composable<FreeBoardWrite> {
            FreeBoardWriteScreen(
                onNavigateUp = onNavigateUp,
            )
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

fun NavController.navigateToFreeBoardDetailScreen(postId: String) {
    navigate(
        FreeBoardDetail(postId = postId),
    ) {}
}

fun NavController.navigateToFreeBoardWriteScreen() {
    navigate(
        FreeBoardWrite,
    ) {}
}

@Serializable
object FreeBoardBaseRoute

@Serializable
data class FreeBoardDetail(
    val postId: String,
)

@Serializable
data object FreeBoard {
    override fun toString(): String = (this::class.java.toString()).split(" ").last()
}

@Serializable
object FreeBoardWrite

package com.lanpet.free.navigation

import android.os.Build
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.free.screen.FreeBoardDetailScreen
import com.lanpet.free.screen.FreeBoardScreen
import com.lanpet.free.screen.FreeBoardWriteScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.freeNavGraph(
    onNavigateUp: () -> Unit,
    onNavigateToFreeBoardWriteFreeBoard: () -> Unit,
    onNavigateToFreeBoardDetail: (postId: String, profileId: String) -> Unit,
) {
    navigation<FreeBoardBaseRoute>(
        startDestination = FreeBoard,
    ) {
        composable<FreeBoard> {
            FreeBoardScreen(
                onNavigateToFreeBoardWrite = onNavigateToFreeBoardWriteFreeBoard,
                onNavigateToFreeBoardDetail = onNavigateToFreeBoardDetail,
            )
        }
        composable<FreeBoardDetail> {
            val authManager = LocalAuthManager.current

            val postId =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.arguments?.getSerializable("postId", String::class.java)
                        ?: throw IllegalArgumentException("postId is required")
                } else {
                    it.arguments?.getSerializable("postId") as? String
                        ?: throw IllegalArgumentException("postId is required")
                }

            val profileId = authManager.defaultUserProfile.value.id

            argument("args") {
                FreeBoardDetail(
                    postId = postId,
                    profileId = profileId,
                )
            }

            FreeBoardDetailScreen(
                onNavigateUp = onNavigateUp,
            )
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

fun NavController.navigateToFreeBoardDetailScreen(
    postId: String,
    profileId: String,
) {
    navigate(
        FreeBoardDetail(postId = postId, profileId = profileId),
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
    val profileId: String,
)

@Serializable
data object FreeBoard {
    override fun toString(): String = (this::class.java.toString()).split(" ").last()
}

@Serializable
object FreeBoardWrite

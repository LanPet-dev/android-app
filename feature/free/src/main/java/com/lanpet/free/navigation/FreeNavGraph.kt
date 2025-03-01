package com.lanpet.free.navigation

import android.os.Bundle
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.free.screen.FreeBoardCommentDetailScreen
import com.lanpet.free.screen.FreeBoardDetailScreen
import com.lanpet.free.screen.FreeBoardScreen
import com.lanpet.free.screen.FreeBoardWriteScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

fun NavGraphBuilder.freeNavGraph(
    onNavigateUp: () -> Unit,
    onNavigateToFreeBoardCommentDetail: (postId: String, freeBoardComment: FreeBoardComment) -> Unit,
    onNavigateToFreeBoardWriteFreeBoard: () -> Unit,
    onNavigateToFreeBoardDetail: (postId: String, profileId: String, nickname: String, navOptions: NavOptions?) -> Unit,
    navController: NavController,
) {
    navigation<FreeBoardBaseRoute>(
        startDestination = FreeBoard,
    ) {
        composable<FreeBoard> {
            FreeBoardScreen(
                navController = navController,
                onNavigateToFreeBoardWrite = onNavigateToFreeBoardWriteFreeBoard,
                onNavigateToFreeBoardDetail = { postId, profileId, nickname ->
                    onNavigateToFreeBoardDetail(postId, profileId, nickname, null)
                },
            )
        }
        composable<FreeBoardDetail> {
            FreeBoardDetailScreen(
                navController = navController,
                onNavigateUp = onNavigateUp,
                onNavigateToFreeBoardCommentDetail = onNavigateToFreeBoardCommentDetail,
            )
        }
        composable<FreeBoardWrite> {
            FreeBoardWriteScreen(
                onNavigateUp = onNavigateUp,
                onNavigateToFreeBoardDetail = onNavigateToFreeBoardDetail,
            )
        }
        composable<FreeBoardCommentDetail>(
            typeMap =
                mapOf(
                    typeOf<FreeBoardComment>() to freeBoardCommentType,
                ),
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(500),
                ) + fadeIn(animationSpec = tween(500))
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(500),
                ) +
                    fadeOut(
                        animationSpec = tween(500),
                    )
            },
        ) {
            FreeBoardCommentDetailScreen(
                onNavigateUp = onNavigateUp,
            )
        }
    }
}

val freeBoardCommentType =
    object : NavType<FreeBoardComment>(
        isNullableAllowed = false,
    ) {
        override fun get(
            bundle: Bundle,
            key: String,
        ): FreeBoardComment? =
            bundle.getString(key)?.let {
                Json.decodeFromString(it)
            }

        override fun parseValue(value: String): FreeBoardComment = Json.decodeFromString(value)

        override fun put(
            bundle: Bundle,
            key: String,
            value: FreeBoardComment,
        ) {
            bundle.putString(key, Json.encodeToString(FreeBoardComment.serializer(), value))
        }

        override fun serializeAsValue(value: FreeBoardComment): String = Json.encodeToString(FreeBoardComment.serializer(), value)
    }

fun NavController.navigateToFreeBoardCommentDetailScreen(
    postId: String,
    freeBoardComment: FreeBoardComment,
) {
    navigate(
        FreeBoardCommentDetail(
            postId = postId,
            freeBoardComment = freeBoardComment,
        ),
    ) {
        launchSingleTop = true
    }
}

fun NavController.navigateToFreeBoardBaseRoute(navOptions: NavOptions) {
    navigate(
        FreeBoardBaseRoute,
        navOptions,
    )
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
    nickname: String,
    navOptions: NavOptions? = null,
) {
    val defaultNavOptions =
        NavOptions
            .Builder()
            .setLaunchSingleTop(true)
            .apply {
                navOptions?.let { options ->
                    setPopUpTo(options.popUpToId, options.isPopUpToInclusive())
                }
            }.build()

    navigate(
        route = FreeBoardDetail(postId = postId, profileId = profileId, nickname = nickname),
        navOptions = defaultNavOptions,
    )
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
    val nickname: String,
)

@Serializable
data class FreeBoardCommentDetail(
    val postId: String,
    val freeBoardComment: FreeBoardComment,
)

@Serializable
data object FreeBoard {
    override fun toString(): String = (this::class.java.toString()).split(" ").last()
}

@Serializable
object FreeBoardWrite

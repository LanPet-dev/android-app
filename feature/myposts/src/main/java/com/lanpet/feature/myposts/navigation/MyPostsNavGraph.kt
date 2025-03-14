package com.lanpet.feature.myposts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lanpet.feature.myposts.MyPostsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.myPostsNavGraph(
    onNavigateUp: () -> Unit,
    onNavigateToFreeBoardDetail: (postId: String, profileId: String, nickname: String) -> Unit,
) {
    composable<MyPosts> {
        MyPostsScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToFreeBoardDetail = onNavigateToFreeBoardDetail,
        )
    }
}

fun NavController.navigateToMyPosts(profileId: String) {
    this.navigate(MyPosts(profileId))
}

@Serializable
data class MyPosts(
    val profileId: String,
)

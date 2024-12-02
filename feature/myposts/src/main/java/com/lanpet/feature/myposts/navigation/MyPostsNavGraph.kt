package com.lanpet.feature.myposts.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.lanpet.feature.myposts.MyPostsScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.myPostsNavGraph(
    onNavigateUp: () -> Unit,
) {
    composable<MyPosts> {
        MyPostsScreen(
            onNavigateUp = onNavigateUp
        )
    }
}

fun NavController.navigateToMyPosts() {
    this.navigate(MyPosts)
}

@Serializable
object MyPosts
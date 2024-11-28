package com.lanpet.myprofile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.myprofile.MyProfileScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.myProfileNavGraph() {
    navigation<MyProfileBaseRoute>(
        startDestination = MyProfile,
    ) {
        composable<MyProfile> {
            MyProfileScreen()
        }
    }
}

fun NavController.navigateToMyProfile() {
    navigate(
        MyProfile,
    ) {
        popUpTo(0) {
            inclusive = true
        }
    }
}

@Serializable
object MyProfileBaseRoute

@Serializable
object MyProfile
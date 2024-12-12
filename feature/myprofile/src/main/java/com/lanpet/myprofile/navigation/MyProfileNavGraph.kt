package com.lanpet.myprofile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.myprofile.screen.MyProfileAddProfileScreen
import com.lanpet.myprofile.screen.MyProfileCreateProfileScreen
import com.lanpet.myprofile.screen.MyProfileScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.myProfileNavGraph(
    onNavigateUp: () -> Unit,
    onNavigateToMyProfileCreateProfile: () -> Unit,
    onNavigateToMyProfileAddProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToMyPosts: () -> Unit,
) {
    navigation<MyProfileBaseRoute>(
        startDestination = MyProfile,
    ) {
        composable<MyProfile>(
        ) {
            MyProfileScreen(
                onNavigateToProfileCreate = onNavigateToMyProfileCreateProfile,
                onNavigateToSettings = onNavigateToSettings,
                onNavigateToMyPosts = onNavigateToMyPosts
            )
        }
        composable<MyProfileCreateProfile> {
            MyProfileCreateProfileScreen(
                onNavigateUp = {
                    onNavigateUp()
                },
                onNavigateToAddProfile = onNavigateToMyProfileAddProfile,
            )
        }
        composable<MyProfileAddProfile> {
            MyProfileAddProfileScreen(
                onClose = {
                    onNavigateUp()
                }
            )

        }
    }
}

fun NavController.navigateToMyProfileBaseRoute() {
    navigate(
        MyProfileBaseRoute,
    ) {
        launchSingleTop = true
        popUpTo(0) {
            inclusive = true
        }
    }
}

fun NavController.navigateToMyProfileAddProfile() {
    navigate(
        MyProfileAddProfile,
    ) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToMyProfileCreateProfile() {
    navigate(
        MyProfileCreateProfile,
    ) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToMyProfile() {
    navigate(
        MyProfile,
    ) {
        launchSingleTop = true
        restoreState = true
//        popUpTo(0) {
//            inclusive = true
//        }
    }
}

@Serializable
object MyProfileBaseRoute

@Serializable
data object MyProfile {
    override fun toString(): String {
        return (this::class.java.toString()).split(" ").last()
    }
}

@Serializable
object MyProfileCreateProfile

@Serializable
object MyProfileAddProfile
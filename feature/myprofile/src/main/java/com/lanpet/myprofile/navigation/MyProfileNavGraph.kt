package com.lanpet.myprofile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.myprofile.screen.MyProfileAddProfileScreen
import com.lanpet.myprofile.screen.MyProfileCreateProfileScreen
import com.lanpet.myprofile.screen.MyProfileModifyProfileScreen
import com.lanpet.myprofile.screen.MyProfileScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.myProfileNavGraph(
    onNavigateUp: () -> Unit,
    onNavigateToMyProfileCreateProfile: () -> Unit,
    onNavigateToMyProfileModifyProfile: () -> Unit,
    onNavigateToMyProfileAddProfile: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToMyPosts: () -> Unit,
) {
    navigation<MyProfileBaseRoute>(
        startDestination = MyProfile,
    ) {
        composable<MyProfile> {
            MyProfileScreen(
                onNavigateToProfileCreate = onNavigateToMyProfileCreateProfile,
                onNavigateToSettings = onNavigateToSettings,
                onNavigateToMyPosts = onNavigateToMyPosts,
            )
        }
        composable<MyProfileCreateProfile> {
            MyProfileCreateProfileScreen(
                onNavigateUp = {
                    onNavigateUp()
                },
                onNavigateToAddProfile = onNavigateToMyProfileAddProfile,
                onNavigateToModifyProfile = onNavigateToMyProfileModifyProfile,
            )
        }
        composable<MyProfileAddProfile> {
            MyProfileAddProfileScreen(
                onClose = {
                    onNavigateUp()
                },
            )
        }
        composable<MyProfileModifyProfile> {
            MyProfileModifyProfileScreen(
                onClose = {
                    onNavigateUp()
                },
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

fun NavController.navigateToMyProfileModifyProfile() {
    navigate(
        MyProfileModifyProfile,
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
    override fun toString(): String = (this::class.java.toString()).split(" ").last()
}

@Serializable
object MyProfileCreateProfile

@Serializable
object MyProfileAddProfile

@Serializable
object MyProfileModifyProfile

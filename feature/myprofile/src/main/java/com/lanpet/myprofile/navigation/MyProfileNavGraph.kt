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
) {
    navigation<MyProfileBaseRoute>(
        startDestination = MyProfile,
    ) {
        composable<MyProfile>(
        ) {
            MyProfileScreen(
                onNavigateToProfileCreate = onNavigateToMyProfileCreateProfile
            )
        }
        composable<MyProfileCreateProfile> {
            MyProfileCreateProfileScreen(
                onNavigateUp = {
                    onNavigateUp()
                },
                onNavigateToAddProfile = onNavigateToMyProfileAddProfile
            )
        }
    }
}

fun NavController.navigateToMyProfileAddProfile() {
    navigate(
        MyProfileAddProfile,
    )
}

fun NavController.navigateToMyProfileCreateProfile() {
    navigate(
        MyProfileCreateProfile,
    )
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

@Serializable
object MyProfileCreateProfile

@Serializable
object MyProfileAddProfile
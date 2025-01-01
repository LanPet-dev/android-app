package com.lanpet.myprofile.navigation

import android.os.Build
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.domain.model.ProfileType
import com.lanpet.myprofile.screen.MyProfileAddProfileScreen
import com.lanpet.myprofile.screen.MyProfileCreateProfileScreen
import com.lanpet.myprofile.screen.MyProfileManageProfileScreen
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
    onNavigateToMyProfileManageProfile: (String, ProfileType) -> Unit,
) {
    navigation<MyProfileBaseRoute>(
        startDestination = MyProfile,
    ) {
        composable<MyProfile> {
            MyProfileScreen(
                onNavigateToProfileCreate = onNavigateToMyProfileCreateProfile,
                onNavigateToSettings = onNavigateToSettings,
                onNavigateToMyPosts = onNavigateToMyPosts,
                onNavigateToProfileManage = onNavigateToMyProfileManageProfile,
            )
        }
        composable<MyProfileCreateProfile> {
            MyProfileCreateProfileScreen(
                onNavigateUp = {
                    onNavigateUp()
                },
                onNavigateToAddProfile = onNavigateToMyProfileAddProfile,
                onNavigateToModifyProfile = onNavigateToMyProfileManageProfile,
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
        composable<MyProfileManageProfile>
        {
            val profileId =
                it.arguments?.getString("profileId")
                    ?: throw IllegalArgumentException("profileId is required")
            val profileType =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.arguments?.getSerializable("profileType", ProfileType::class.java)
                        ?: throw IllegalArgumentException("profileType is required")
                } else {
                    it.arguments?.getSerializable("profileType") as? ProfileType
                        ?: throw IllegalArgumentException("profileType is required")
                }

            argument("args") {
                MyProfileManageProfile(
                    profileId = profileId,
                    profileType = profileType,
                )
            }

            MyProfileManageProfileScreen(
                args =
                    MyProfileManageProfile(
                        profileId = profileId,
                        profileType = profileType,
                    ),
                onNavigateUp = onNavigateUp,
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

fun NavController.navigateToMyProfileSetDefaultProfile() {
    navigate(
        MyProfileSetDefaultProfile,
    ) {
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateToMyProfileManageProfile(
    profileId: String,
    profileType: ProfileType,
) {
    navigate(
        MyProfileManageProfile(
            profileId = profileId,
            profileType = profileType,
        ),
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

@Serializable
object MyProfileSetDefaultProfile

@Serializable
data class MyProfileManageProfile(
    val profileId: String,
    val profileType: ProfileType,
)

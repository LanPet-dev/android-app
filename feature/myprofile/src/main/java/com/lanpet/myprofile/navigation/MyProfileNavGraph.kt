package com.lanpet.myprofile.navigation

import android.os.Build
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.domain.model.ProfileType
import com.lanpet.myprofile.screen.MyProfileAddProfileEntryScreen
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
    onNavigateToMyProfileAddProfile: (ProfileType) -> Unit,
    onNavigateToMyProfileAddProfileEntry: () -> Unit,
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
                onNavigateToAddProfileEntry = onNavigateToMyProfileAddProfileEntry,
                onNavigateToModifyProfile = onNavigateToMyProfileManageProfile,
            )
        }
        composable<MyProfileAddProfileEntry> {
            MyProfileAddProfileEntryScreen(
                onNavigateUp = {
                    onNavigateUp()
                },
                onNavigateToAddPetProfile = {
                    onNavigateToMyProfileAddProfile(ProfileType.PET)
                },
                onNavigateToAddManProfile = {
                    onNavigateToMyProfileAddProfile(ProfileType.BUTLER)
                },
            )
        }

        composable<MyProfileAddProfile> {
            val profileType =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.arguments?.getSerializable("profileType", ProfileType::class.java)
                        ?: throw IllegalArgumentException("profileType is required")
                } else {
                    it.arguments?.getSerializable("profileType") as? ProfileType
                        ?: throw IllegalArgumentException("profileType is required")
                }

            argument("args") {
                MyProfileAddProfile(
                    profileType = profileType,
                )
            }

            MyProfileAddProfileScreen(
                args =
                    MyProfileAddProfile(
                        profileType = profileType,
                    ),
                onNavigateUp = {
                    onNavigateUp()
                },
            )
        }
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

fun NavController.navigateToMyProfileBaseRoute(navOptions: NavOptions) {
    navigate(
        MyProfileBaseRoute,
        navOptions,
    )
}

fun NavController.navigateToMyProfileAddProfile(profileType: ProfileType) {
    navigate(
        MyProfileAddProfile(
            profileType = profileType,
        ),
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

fun NavController.navigateToMyProfileAddProfileEntry() {
    navigate(
        MyProfileAddProfileEntry,
    ) {
        launchSingleTop = true
        restoreState = true
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
data class MyProfileAddProfile(
    val profileType: ProfileType,
)

@Serializable
object MyProfileModifyProfile

@Serializable
object MyProfileSetDefaultProfile

@Serializable
data class MyProfileManageProfile(
    val profileId: String,
    val profileType: ProfileType,
)

@Serializable
object MyProfileAddProfileEntry

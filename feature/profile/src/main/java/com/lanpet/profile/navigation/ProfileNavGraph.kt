package com.lanpet.profile.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.lanpet.profile.screen.ProfileCreateDoneScreen
import com.lanpet.profile.screen.ProfileCreateHasPetScreen
import com.lanpet.profile.screen.nopet.ProfileCreateHumanAgeScreen
import com.lanpet.profile.screen.nopet.ProfileCreateHumanBioScreen
import com.lanpet.profile.screen.nopet.ProfileCreateNoPetIntroScreen
import com.lanpet.profile.screen.nopet.ProfileCreateNoPetNameScreen
import com.lanpet.profile.screen.nopet.ProfileCreatePreferPetScreen
import com.lanpet.profile.screen.yespet.ProfileCreatePetBioScreen
import com.lanpet.profile.screen.yespet.ProfileCreatePetCategoryScreen
import com.lanpet.profile.screen.yespet.ProfileCreatePetSpeciesScreen
import com.lanpet.profile.screen.yespet.ProfileCreateYesPetIntroScreen
import com.lanpet.profile.screen.yespet.ProfileCreateYesPetNameScreen
import com.lanpet.profile.viewmodel.ManProfileCreateViewModel
import com.lanpet.profile.viewmodel.PetProfileCreateViewModel
import kotlinx.serialization.Serializable

fun NavGraphBuilder.profileNavGraph(
    onNavigateToYesPetNameScreen: () -> Unit,
    onNavigateToNoPetNameScreen: () -> Unit,
    onNavigateToYesPetIntroScreen: () -> Unit,
    onNavigateToNoPetIntroScreen: () -> Unit,
    onNavigateToHumanBio: () -> Unit,
    onNavigateToPetBio: () -> Unit,
    onNavigateToPetCategory: () -> Unit,
    onNavigateToPetSpecies: () -> Unit,
    onNavigateToHumanAge: () -> Unit,
    onNavigateToDone: () -> Unit,
    onNavigateToPreferPet: () -> Unit,
    onNavigateToMain: () -> Unit,
    navController: NavController,
) {
    composable<ProfileCreateHasPet> {
        ProfileCreateHasPetScreen(
            onNavigateToYesPetScreen = onNavigateToYesPetIntroScreen,
            onNavigateToNoPetScreen = onNavigateToNoPetIntroScreen,
        )
    }

    navigation<ProfileNoPetBaseRoute>(
        startDestination = ProfileIntroNoPet,
    ) {
        composable<ProfileIntroNoPet> {
            ProfileCreateNoPetIntroScreen(
                onNavigateToNoPetNameScreen = onNavigateToNoPetNameScreen,
            )
        }

        composable<ProfileCreateNoPetName> { backStackEntry ->
            val parentEntry = remember { navController.getBackStackEntry(ProfileNoPetBaseRoute) }
            val viewModel = hiltViewModel<ManProfileCreateViewModel>(parentEntry)

            ProfileCreateNoPetNameScreen(
                onNavigateToHumanAge = onNavigateToHumanAge,
                manProfileCreateViewModel = viewModel,
            )
        }

        composable<ProfileCreateDone> {
            ProfileCreateDoneScreen(
                onNavigateToMyProfile = onNavigateToMain,
            )
        }

        composable<ProfileCreateHumanBio> { backStackEntry ->
            val parentEntry = remember { navController.getBackStackEntry(ProfileNoPetBaseRoute) }
            val viewModel = hiltViewModel<ManProfileCreateViewModel>(parentEntry)

            ProfileCreateHumanBioScreen(
                onRegisterManProfileComplete = onNavigateToDone,
                manProfileCreateViewModel = viewModel,
            )
        }

        composable<ProfileCreateHumanAge> { backStackEntry ->
            val parentEntry = remember { navController.getBackStackEntry(ProfileNoPetBaseRoute) }
            val viewModel = hiltViewModel<ManProfileCreateViewModel>(parentEntry)

            ProfileCreateHumanAgeScreen(
                onNavigateToPreferPet = onNavigateToPreferPet,
                manProfileCreateViewModel = viewModel,
            )
        }

        composable<ProfileCreatePreferPet> { backStackEntry ->
            val parentEntry = remember { navController.getBackStackEntry(ProfileNoPetBaseRoute) }
            val viewModel = hiltViewModel<ManProfileCreateViewModel>(parentEntry)

            ProfileCreatePreferPetScreen(
                onNavigateToHumanBio = onNavigateToHumanBio,
                manProfileCreateViewModel = viewModel,
            )
        }
    }

    navigation<ProfileYesPetBaseRoute>(
        startDestination = ProfileIntroYesPet,
    ) {
        composable<ProfileIntroYesPet> {
            ProfileCreateYesPetIntroScreen(
                onNavigateToYesPetNameScreen = onNavigateToYesPetNameScreen,
            )
        }
        composable<ProfileCreateYesPetName> { backStackEntry ->
            val parentEntry = remember { navController.getBackStackEntry(ProfileYesPetBaseRoute) }
            val viewModel = hiltViewModel<PetProfileCreateViewModel>(parentEntry)

            ProfileCreateYesPetNameScreen(
                onNavigateToPetCategory = onNavigateToPetCategory,
                petProfileCreateViewModel = viewModel,
            )
        }

        composable<ProfileCreateDone> {
            ProfileCreateDoneScreen(
                onNavigateToMyProfile = onNavigateToMain,
            )
        }

        composable<ProfileCreatePetBio> { backStackEntry ->
            val parentEntry = remember { navController.getBackStackEntry(ProfileYesPetBaseRoute) }
            val viewModel = hiltViewModel<PetProfileCreateViewModel>(parentEntry)

            ProfileCreatePetBioScreen(
                onRegisterPetProfileComplete = onNavigateToDone,
                petProfileCreateViewModel = viewModel,
            )
        }

        composable<ProfileCreatePetCategory> { backStackEntry ->
            val parentEntry = remember { navController.getBackStackEntry(ProfileYesPetBaseRoute) }
            val viewModel = hiltViewModel<PetProfileCreateViewModel>(parentEntry)

            ProfileCreatePetCategoryScreen(
                onNavigateToPetSpecies = onNavigateToPetSpecies,
                petProfileCreateViewModel = viewModel,
            )
        }

        composable<ProfileCreatePetSpecies> { backStackEntry ->
            val parentEntry = remember { navController.getBackStackEntry(ProfileYesPetBaseRoute) }
            val viewModel = hiltViewModel<PetProfileCreateViewModel>(parentEntry)

            ProfileCreatePetSpeciesScreen(
                onNavigateToPetBio = onNavigateToPetBio,
                petProfileCreateViewModel = viewModel,
            )
        }
    }
}

fun NavController.navigateToProfileCreateHasPet() {
    this.navigate(ProfileCreateHasPet)
}

fun NavController.navigateToProfileCreateYesPetName() {
    this.navigate(ProfileCreateYesPetName)
}

fun NavController.navigateToProfileCreateNoPetName() {
    this.navigate(ProfileCreateNoPetName)
}

fun NavController.navigateToProfileIntroYesPet() {
    this.navigate(ProfileIntroYesPet)
}

fun NavController.navigateToProfileIntroNoPet() {
    this.navigate(ProfileIntroNoPet)
}

fun NavController.navigateToProfileCreateDone() {
    this.navigate(ProfileCreateDone)
}

fun NavController.navigateToProfileCreateHumanBio() {
    this.navigate(ProfileCreateHumanBio)
}

fun NavController.navigateToProfileCreatePetBio() {
    this.navigate(ProfileCreatePetBio)
}

fun NavController.navigateToProfileCreatePetCategory() {
    this.navigate(ProfileCreatePetCategory)
}

fun NavController.navigateToProfileCreatePetSpecies() {
    this.navigate(ProfileCreatePetSpecies)
}

fun NavController.navigateToProfileCreateHumanAge() {
    this.navigate(ProfileCreateHumanAge)
}

fun NavController.navigateToProfileCreatePreferPet() {
    this.navigate(ProfileCreatePreferPet)
}

@Serializable
object ProfileNoPetBaseRoute

@Serializable
object ProfileYesPetBaseRoute

@Serializable
object ProfileCreateHasPet

@Serializable
object ProfileCreateNoPetName

@Serializable
object ProfileCreateYesPetName

@Serializable
object ProfileIntroYesPet

@Serializable
object ProfileIntroNoPet

@Serializable
object ProfileCreateDone

@Serializable
object ProfileCreateHumanBio

@Serializable
object ProfileCreatePetBio

@Serializable
object ProfileCreatePetCategory

@Serializable
object ProfileCreatePetSpecies

@Serializable
object ProfileCreateHumanAge

@Serializable
object ProfileCreatePreferPet

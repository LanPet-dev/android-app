package com.lanpet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.auth.navigation.authNavGraph
import com.example.auth.navigation.navigateToLoginScreen
import com.example.landing.navigation.landingNavGraph
import com.lanpet.core.auth.LocalAuthViewModel
import com.lanpet.profile.navigation.ProfileCreateHasPet
import com.lanpet.profile.navigation.navigateToProfileCreateDone
import com.lanpet.profile.navigation.navigateToProfileCreateHumanAge
import com.lanpet.profile.navigation.navigateToProfileCreateHumanBio
import com.lanpet.profile.navigation.navigateToProfileCreateNoPetName
import com.lanpet.profile.navigation.navigateToProfileCreatePetBio
import com.lanpet.profile.navigation.navigateToProfileCreatePetCategory
import com.lanpet.profile.navigation.navigateToProfileCreatePetSpecies
import com.lanpet.profile.navigation.navigateToProfileCreatePreferPet
import com.lanpet.profile.navigation.navigateToProfileCreateYesPetName
import com.lanpet.profile.navigation.navigateToProfileIntroNoPet
import com.lanpet.profile.navigation.navigateToProfileIntroYesPet
import com.lanpet.profile.navigation.profileNavGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val authViewModel = LocalAuthViewModel.current

    val authState = authViewModel.authState.collectAsState()

    // Handling navigation by AuthState
    rememberNavigationHandler(navController, authState.value)

    NavHost(
        navController = navController,
        startDestination = ProfileCreateHasPet,
    ) {
        landingNavGraph {
            navController.navigateToLoginScreen()
        }

        authNavGraph()

        profileNavGraph(
            onNavigateToYesPetNameScreen = {
                navController.navigateToProfileCreateYesPetName()
            },
            onNavigateToNoPetNameScreen = {
                navController.navigateToProfileCreateNoPetName()
            },
            onNavigateToYesPetIntroScreen = {
                navController.navigateToProfileIntroYesPet()
            },
            onNavigateToNoPetIntroScreen = {
                navController.navigateToProfileIntroNoPet()
            },
            onNavigateToHumanBio = { navController.navigateToProfileCreateHumanBio() },
            onNavigateToPetBio = { navController.navigateToProfileCreatePetBio() },
            onNavigateToPetCategory = { navController.navigateToProfileCreatePetCategory() },
            onNavigateToPetSpecies = { navController.navigateToProfileCreatePetSpecies() },
            onNavigateToHumanAge = { navController.navigateToProfileCreateHumanAge() },
            onNavigateToDone = { navController.navigateToProfileCreateDone() },
            onNavigateToPreferPet = { navController.navigateToProfileCreatePreferPet() },
            onNavigateToMain = { navController.navigateToMainScreen() },
            navController = navController
        )

        composable<MainNavigationRoute> {
            val selectedNavItem = it.toRoute<MainNavigationRoute>().selectedNavItem

            MainScreen(selectedNavItem, navController)
        }

    }
}

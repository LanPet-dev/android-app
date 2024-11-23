package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.auth.navigation.authNavGraph
import com.example.landing.navigation.Landing
import com.example.landing.navigation.landingNavGraph
import com.example.profile.navigation.navigateToProfileCreateDone
import com.example.profile.navigation.navigateToProfileCreateHasPet
import com.example.profile.navigation.navigateToProfileCreateHumanAge
import com.example.profile.navigation.navigateToProfileCreateHumanBio
import com.example.profile.navigation.navigateToProfileCreateNoPetName
import com.example.profile.navigation.navigateToProfileCreatePetBio
import com.example.profile.navigation.navigateToProfileCreatePetCategory
import com.example.profile.navigation.navigateToProfileCreatePetSpecies
import com.example.profile.navigation.navigateToProfileCreatePreferPet
import com.example.profile.navigation.navigateToProfileCreateYesPetName
import com.example.profile.navigation.navigateToProfileIntroNoPet
import com.example.profile.navigation.navigateToProfileIntroYesPet
import com.example.profile.navigation.profileNavGraph

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Landing,
    ) {
        landingNavGraph {
            println("navigate to login screen")
//            navController.navigateToLoginScreen()
            navController.navigateToProfileCreateHasPet()
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
        )
    }
}

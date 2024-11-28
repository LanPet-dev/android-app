package com.lanpet.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.auth.navigation.authNavGraph
import com.example.auth.navigation.navigateToLoginScreen
import com.example.landing.navigation.Landing
import com.example.landing.navigation.landingNavGraph
import com.example.model.AuthState
import com.lanpet.auth.LocalAuthViewModel
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
    var currentAuthState: AuthState by remember {
        mutableStateOf(AuthState.Initial)
    }

    /**
     * AuthState 에 따른 Screen 이동 처리를 담당한다.
     * AuthState 가 변경되면, 이전 AuthState 와 비교하여 Screen 이동을 처리함.
     */
    LaunchedEffect(authState.value) {
        // 이전 상태와 변경된 상태가 같다면, 아무것도 하지 않음
        if ((currentAuthState is AuthState.Fail && authState.value is AuthState.Fail)
            || (currentAuthState is AuthState.Success && authState.value is AuthState.Success || (
                    currentAuthState is AuthState.Loading && authState.value is AuthState.Loading
                    ) || (currentAuthState is AuthState.Initial && authState.value is AuthState.Initial)
                    )
        ) {
            return@LaunchedEffect
        }

        // Fail 또는 initial 상태에서 Success로 변경되었다면, 로그인 성공이므로 MainScreen 으로 이동
        if (authState.value is AuthState.Success && (currentAuthState is AuthState.Fail || currentAuthState is AuthState.Initial)) {
            navController.navigateToMainScreen()
        } else if (// 현재상태가 Success 상태에서 Initial 또는 Fail 상태로 변경되었다면 로그인 실패 또는 로그아웃 이므로 Login screen 으로 이동
            (authState.value is AuthState.Initial || authState.value is AuthState.Fail) && currentAuthState is AuthState.Success
        ) {
            navController.navigateToLoginScreen()
        }

        currentAuthState = authState.value
    }

    NavHost(
        navController = navController,
        startDestination = Landing,
    ) {
        landingNavGraph {
            println("navigate to login screen")
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

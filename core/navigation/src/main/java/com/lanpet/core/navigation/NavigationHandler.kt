package com.lanpet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.lanpet.core.common.widget.BottomNavItem
import com.lanpet.domain.model.AuthState
import com.lanpet.feature.auth.navigation.navigateToLoginScreen
import com.lanpet.profile.navigation.navigateToProfileCreateHasPet
import timber.log.Timber

class NavigationHandler(
    private val navController: NavHostController,
) {
    /**
     * 이전 AuthState
     * 현재 AuthState 와 비교하여 처리하기위해 필요함
     */
    private var previousAuthState: AuthState = AuthState.Initial()

    /**
     * AuthState 에 따른 Screen 이동 처리를 담당한다.
     * AuthState 가 변경되면, 이전 AuthState 와 비교하여 Screen 이동을 처리함.
     */
    fun handleNavigationByAuthState(currentAuthState: AuthState) {
        Timber.d(
            "previousAuthState: $previousAuthState, currentAuthState: $currentAuthState " +
                "navigationHandleFlag: ${currentAuthState.navigationHandleFlag}",
        )

        // navigationHandleFlag 가 false 일 경우, Screen 이동 처리를 하지 않습니다.
        if (!currentAuthState.navigationHandleFlag) {
            return
        }

        // navigationHandleFlag 로 처리하는것으로 변경되어, 더이상 이전 AuthState 와 비교할 필요가 없습니다.
//        if (previousAuthState::class == currentAuthState::class ) {
//            Timber.d("previousAuthState is same as currentAuthState")
//            return
//        }

        when (currentAuthState) {
            is AuthState.Initial -> {
                // TODO
            }

            is AuthState.Success -> {
//                 only for test
//                navController.navigateToProfileCreateHasPet()
                if (currentAuthState.profile.isEmpty()) {
                    navController.navigateToProfileCreateHasPet()
                } else {
                    navController.navigateToMainScreen(
                        bottomNavItem = BottomNavItem.Wiki,
                    )
                }
            }

            is AuthState.Logout -> {
                navController.navigateToLoginScreen()
            }

            is AuthState.Fail -> {
                navController.navigateToLoginScreen()
            }

            is AuthState.Loading -> {
                // TODO
            }
        }

        previousAuthState = currentAuthState
    }
}

@Composable
fun rememberNavigationHandler(
    navController: NavHostController,
    currentAuthState: AuthState,
): NavigationHandler {
    val navigationHandler =
        remember {
            NavigationHandler(navController)
        }

    // initialize handle
    LaunchedEffect(currentAuthState) {
        navigationHandler.handleNavigationByAuthState(currentAuthState)
    }

    return navigationHandler
}

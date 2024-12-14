package com.lanpet.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.lanpet.domain.model.AuthState
import com.lanpet.feature.auth.navigation.navigateToLoginScreen

class NavigationHandler(
    private val navController: NavHostController,
) {
    /**
     * 이전 AuthState
     * 현재 AuthState 와 비교하여 처리하기위해 필요함
     */
    private var previousAuthState: AuthState = AuthState.Initial

    /**
     * AuthState 에 따른 Screen 이동 처리를 담당한다.
     * AuthState 가 변경되면, 이전 AuthState 와 비교하여 Screen 이동을 처리함.
     */
    fun handleNavigationByAuthState(currentAuthState: AuthState) {
        println(
            "previousAuthState: $previousAuthState, currentAuthState: $currentAuthState",
        )
        if ((previousAuthState is AuthState.Fail && currentAuthState is AuthState.Fail) ||
            (
                previousAuthState is AuthState.Success &&
                    currentAuthState is AuthState.Success ||
                    (
                        previousAuthState is AuthState.Loading && currentAuthState is AuthState.Loading
                    ) ||
                    (previousAuthState is AuthState.Initial && currentAuthState is AuthState.Initial)
            )
        ) {
            previousAuthState = currentAuthState
            return
        }

        // Fail, Loading 또는 initial 상태에서 Success로 변경되었다면, 로그인 성공이므로 MainScreen 으로 이동
        if (currentAuthState is AuthState.Success &&
            (previousAuthState is AuthState.Fail || previousAuthState is AuthState.Initial || previousAuthState is AuthState.Loading)
        ) {
            navController.navigateToMainScreen()
        } else if ( // 현재상태가 Success 상태에서 Initial 또는 Fail 상태로 변경되었다면 로그인 실패 또는 로그아웃 이므로 Login screen 으로 이동
            (currentAuthState is AuthState.Initial || currentAuthState is AuthState.Fail) && previousAuthState is AuthState.Success
        ) {
            navController.navigateToLoginScreen()
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

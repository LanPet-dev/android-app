package com.lanpet.core.navigation

import androidx.navigation.NavController
import com.lanpet.core.common.widget.BottomNavItem
import kotlinx.serialization.Serializable

fun NavController.navigateToMainScreen(bottomNavItem: BottomNavItem = BottomNavItem.MyPage) {
    navigate(MainNavigationRoute(bottomNavItem)) {
        popUpTo(0) {
            inclusive = true
        }
    }
}

@Serializable
data class MainNavigationRoute(
    val selectedNavItem: BottomNavItem,
)

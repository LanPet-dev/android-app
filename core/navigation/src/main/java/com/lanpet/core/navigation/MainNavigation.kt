package com.lanpet.core.navigation

import androidx.navigation.NavController
import com.lanpet.core.common.widget.BottomNavItem
import com.lanpet.free.navigation.FreeBoardBaseRoute
import com.lanpet.myprofile.navigation.MyProfileBaseRoute
import com.lanpet.wiki.navigation.WikiBaseRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMainScreen(
    idRes: Int,
    bottomNavItem: BottomNavItem = BottomNavItem.MyPage,
) {
    navigate(getBottomNavItemRoute(bottomNavItem)) {
        popUpTo(idRes) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun getBottomNavItemRoute(bottomNavItem: BottomNavItem): Any =
    when (bottomNavItem) {
        BottomNavItem.MyPage -> MyProfileBaseRoute
        BottomNavItem.Wiki -> WikiBaseRoute
        BottomNavItem.Free -> FreeBoardBaseRoute
    }

@Serializable
data object MainNavigationRoute

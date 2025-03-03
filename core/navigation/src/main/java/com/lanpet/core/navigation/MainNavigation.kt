package com.lanpet.core.navigation

import android.annotation.SuppressLint
import androidx.navigation.NavController
import com.lanpet.core.common.widget.BottomNavItem
import com.lanpet.free.navigation.FreeBoardBaseRoute
import com.lanpet.myprofile.navigation.MyProfileBaseRoute
import com.lanpet.wiki.navigation.WikiBaseRoute
import kotlinx.serialization.Serializable
import timber.log.Timber

@SuppressLint("RestrictedApi")
fun NavController.navigateToMainScreen(
    bottomNavItem: BottomNavItem = BottomNavItem.MyPage,
) {
    val startDestinationId =
        this.currentBackStack.value
            .first()
            .destination.id
    Timber.i("navigateToMainScreen :: idRes = $startDestinationId, bottomNavItem = $bottomNavItem")
    Timber.i("navigateToMainScreen :: currentDestination = ${currentDestination?.route}")
    Timber.i("navigateToMainScreen :: currentBackStackEntry = ${currentBackStackEntry?.destination?.route}")
    Timber.i("navigateToMainScreen :: currentBackStackEntryId = ${currentBackStackEntry?.destination?.id}")

    navigate(getBottomNavItemRoute(bottomNavItem)) {
        popUpTo(startDestinationId) {
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

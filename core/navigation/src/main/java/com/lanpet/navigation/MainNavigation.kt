package com.lanpet.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.designsystem.theme.widgets.BottomNavItem
import com.example.designsystem.theme.widgets.LanPetBottomNavBar
import com.lanpet.myprofile.navigation.MyProfileBaseRoute
import com.lanpet.myprofile.navigation.myProfileNavGraph
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import com.example.designsystem.theme.LanPetAppTheme
import com.example.landing.navigation.Landing
import com.example.landing.navigation.navigateToLandingScreen
import com.lanpet.free.navigation.FreeBoardBaseRoute
import com.lanpet.free.navigation.freeNavGraph
import com.lanpet.free.navigation.navigateToFreeBoardScreen
import com.lanpet.myprofile.navigation.navigateToMyProfile
import com.lanpet.profile.navigation.navigateToProfileCreatePetBio
import com.lanpet.wiki.navigation.WikiBaseRoute
import com.lanpet.wiki.navigation.navigateToWikiScreen
import com.lanpet.wiki.navigation.wikiNavGraph
import kotlinx.serialization.Serializable

@Composable
fun MainScreen(selectedNavItem: BottomNavItem, parentNavController: NavHostController) {
    val navController = rememberNavController()

    var navItem by rememberSaveable {
        mutableStateOf(selectedNavItem)
    }

    LaunchedEffect(navItem) {
        when (navItem) {
            BottomNavItem.Wiki -> {
                navController.navigateToWikiScreen()
            }

            BottomNavItem.Free -> {
                navController.navigateToFreeBoardScreen()
            }

            BottomNavItem.MyPage -> {
                navController.navigateToMyProfile()
            }
        }
    }

    Scaffold(
        bottomBar = {
            LanPetBottomNavBar(
                selectedBottomNavItem = navItem,
                bottomNavItemList = listOf(
                    BottomNavItem.Wiki,
                    BottomNavItem.Free,
                    BottomNavItem.MyPage,
                ),
                onItemSelected = { item ->
                    println("selected bottom nav item: $item")
                    navItem = item
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            MainNavigation(
                navController = navController,
                parentNavController = parentNavController,
                startDestination = selectedNavItem
            )
        }
    }

}

@Composable
fun MainNavigation(
    navController: NavHostController,
    parentNavController: NavHostController,
    startDestination: BottomNavItem,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination =
        when (startDestination) {
            BottomNavItem.MyPage -> MyProfileBaseRoute
            BottomNavItem.Free -> FreeBoardBaseRoute
            BottomNavItem.Wiki -> WikiBaseRoute
        },
        modifier = modifier
    ) {
        myProfileNavGraph()
        freeNavGraph()
        wikiNavGraph()
    }
}

fun NavController.navigateToMainScreen(bottomNavItem: BottomNavItem = BottomNavItem.MyPage) {
    navigate(MainNavigationRoute(bottomNavItem)) {
        popUpTo(0) {
            inclusive = true
        }
    }
}

@Serializable
data class MainNavigationRoute(val selectedNavItem: BottomNavItem)


@Composable
@PreviewLightDark
fun MainScreenPreview() {
    LanPetAppTheme {
        MainScreen(selectedNavItem = BottomNavItem.MyPage, rememberNavController())
    }
}
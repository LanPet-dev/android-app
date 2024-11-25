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
import com.lanpet.myprofile.navigation.navigateToMyProfile
import kotlinx.serialization.Serializable

@Composable
fun MainScreen(selectedNavItem: BottomNavItem) {
    val navController = rememberNavController()

    var _selectedNavItem by rememberSaveable {
        mutableStateOf(selectedNavItem)
    }

    LaunchedEffect(_selectedNavItem) {
        //only for test
        navController.navigateToMyProfile()
        //TODO: navigate to selectedNavItem
    }

    Scaffold(
        bottomBar = {
            LanPetBottomNavBar(
                selectedBottomNavItem = _selectedNavItem,
                bottomNavItemList = listOf(
                    BottomNavItem.Wiki,
                    BottomNavItem.Free,
                    BottomNavItem.MyPage,
                ),
                onItemSelected = { item ->
                    println("selected bottom nav item: $item")
                    _selectedNavItem = item
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            MainNavigation(
                navController = navController,
            )
        }
    }

}

@Composable
fun MainNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = MyProfileBaseRoute,
        modifier = modifier
    ) {
        myProfileNavGraph()
    }
}

fun NavController.navigateToMainScreen(bottomNavItem: BottomNavItem = BottomNavItem.MyPage) {
    navigate(MainNavigationRoute(bottomNavItem))
}

@Serializable
data class MainNavigationRoute(val selectedNavItem: BottomNavItem)


@Composable
@PreviewLightDark
fun MainScreenPreview() {
    MainScreen(selectedNavItem = BottomNavItem.MyPage)
}
package com.lanpet.core.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lanpet.myprofile.navigation.MyProfileBaseRoute
import com.lanpet.myprofile.navigation.myProfileNavGraph
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import com.lanpet.core.common.widget.BottomNavItem
import com.lanpet.core.common.widget.LanPetBottomNavBar
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.free.navigation.FreeBoardBaseRoute
import com.lanpet.free.navigation.freeNavGraph
import com.lanpet.free.navigation.navigateToFreeBoardScreen
import com.lanpet.myprofile.navigation.navigateToMyProfile
import com.lanpet.myprofile.navigation.navigateToMyProfileAddProfile
import com.lanpet.myprofile.navigation.navigateToMyProfileCreateProfile
import com.lanpet.wiki.navigation.WikiBaseRoute
import com.lanpet.wiki.navigation.navigateToWikiScreen
import com.lanpet.wiki.navigation.wikiNavGraph
import kotlinx.serialization.Serializable

/**
 * Main screen of the app
 * This screen has child NavHost whose navController is independent from parent navHost.
 * Because of this, we have to use child's navController and parent's navController properly.
 * For instance, if you use child's navController to navigate to a screen that is dependent on parent's navigation graph not to child's navigation graph,
 * then, you may face an issue saying that the destination is not found.
 * TODO: refactor such a difficult management of navigation. fuck.
 */
//@Composable
//fun MainScreen(
//    selectedNavItem: BottomNavItem,
//    parentNavController: NavHostController
//) {
//    val navController = rememberNavController()
//
//    var navItem by rememberSaveable {
//        mutableStateOf(selectedNavItem)
//    }
//
//    LaunchedEffect(navItem) {
//        when (navItem) {
//            BottomNavItem.Wiki -> {
//                navController.navigateToWikiScreen()
//            }
//
//            BottomNavItem.Free -> {
//                navController.navigateToFreeBoardScreen()
//            }
//
//            BottomNavItem.MyPage -> {
//                navController.navigateToMyProfile()
//            }
//        }
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        MainNavigation(
//            navController = navController,
//            parentNavController = parentNavController,
//            startDestination = selectedNavItem,
//            modifier = Modifier.weight(1f)
//        )
//        LanPetBottomNavBar(
//            selectedBottomNavItem = navItem,
//            bottomNavItemList = listOf(
//                BottomNavItem.Wiki,
//                BottomNavItem.Free,
//                BottomNavItem.MyPage,
//            ),
//            onItemSelected = { item ->
//                println("selected bottom nav item: $item")
//                navItem = item
//            }
//        )
//    }
//}
//
//@Composable
//fun MainNavigation(
//    navController: NavHostController,
//    parentNavController: NavHostController,
//    startDestination: BottomNavItem,
//    modifier: Modifier = Modifier
//) {
//    NavHost(
//        navController = navController,
//        startDestination =
//        when (startDestination) {
//            BottomNavItem.MyPage -> MyProfileBaseRoute
//            BottomNavItem.Free -> FreeBoardBaseRoute
//            BottomNavItem.Wiki -> WikiBaseRoute
//        },
//        modifier = modifier
//    ) {
//        myProfileNavGraph(
//            onNavigateUp = {
//                navController.navigateUp()
//            },
//            onNavigateToMyProfileCreateProfile = {
//                navController.navigateToMyProfileCreateProfile()
//            },
//            onNavigateToMyProfileAddProfile = {
//                parentNavController.navigateToMyProfileAddProfile()
//            },
//        )
//        freeNavGraph()
//        wikiNavGraph()
//    }
//}

fun NavController.navigateToMainScreen(bottomNavItem: BottomNavItem = BottomNavItem.MyPage) {
    navigate(MainNavigationRoute(bottomNavItem)) {
        popUpTo(0) {
            inclusive = true
        }
    }
}

@Serializable
data class MainNavigationRoute(val selectedNavItem: BottomNavItem)


//@Composable
//@PreviewLightDark
//fun MainScreenPreview() {
//    LanPetAppTheme {
//        MainScreen(
//            selectedNavItem = BottomNavItem.MyPage,
//            rememberNavController()
//        )
//    }
//}
package com.lanpet.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.widget.BottomNavItem
import com.lanpet.core.common.widget.LanPetBottomNavBar
import com.lanpet.feature.auth.navigation.authNavGraph
import com.lanpet.feature.auth.navigation.navigateToLoginScreen
import com.lanpet.feature.landing.navigation.Landing
import com.lanpet.feature.landing.navigation.landingNavGraph
import com.lanpet.feature.myposts.navigation.myPostsNavGraph
import com.lanpet.feature.myposts.navigation.navigateToMyPosts
import com.lanpet.feature.settings.navigation.navigateToLogoutDialog
import com.lanpet.feature.settings.navigation.navigateToMemberLeave
import com.lanpet.feature.settings.navigation.navigateToSettings
import com.lanpet.feature.settings.navigation.settingsNavGraph
import com.lanpet.free.navigation.FreeBoard
import com.lanpet.free.navigation.freeNavGraph
import com.lanpet.free.navigation.navigateToFreeBoardBaseRoute
import com.lanpet.free.navigation.navigateToFreeBoardDetailScreen
import com.lanpet.free.navigation.navigateToFreeBoardWriteScreen
import com.lanpet.myprofile.navigation.MyProfile
import com.lanpet.myprofile.navigation.MyProfileBaseRoute
import com.lanpet.myprofile.navigation.myProfileNavGraph
import com.lanpet.myprofile.navigation.navigateToMyProfileAddProfile
import com.lanpet.myprofile.navigation.navigateToMyProfileBaseRoute
import com.lanpet.myprofile.navigation.navigateToMyProfileCreateProfile
import com.lanpet.myprofile.navigation.navigateToMyProfileManageProfile
import com.lanpet.myprofile.navigation.navigateToMyProfileModifyProfile
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
import com.lanpet.wiki.navigation.Wiki
import com.lanpet.wiki.navigation.navigateToWikiBaseRoute
import com.lanpet.wiki.navigation.wikiNavGraph
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import timber.log.Timber

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val authManager = LocalAuthManager.current

    val authState = authManager.authState.collectAsState()

    // Handling navigation by AuthState
    rememberNavigationHandler(navController, authState.value)

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var shouldShowBottomBar by remember {
        mutableStateOf(false)
    }

    var navItem by rememberSaveable {
        mutableStateOf(BottomNavItem.Wiki)
    }

    LaunchedEffect(navBackStackEntry?.destination?.route) {
        // 현재 화면이 BottomNav를 표시해야 하는지 확인
        shouldShowBottomBar =
            when (navBackStackEntry?.destination?.route) {
                Wiki.toString() -> {
                    navItem = BottomNavItem.Wiki
                    true
                }

                FreeBoard.toString() -> {
                    navItem = BottomNavItem.Free
                    true
                }

                MyProfile.toString() -> {
                    navItem = BottomNavItem.MyPage
                    true
                }

                toString(),
                -> true

                else -> false
            }
    }

    // BottomNav의 item이 변경되면 해당 item에 맞는 화면으로 이동
    LaunchedEffect(Unit) {
        snapshotFlow { navItem }
            .distinctUntilChanged()
            .drop(1) // 초기 값은 스킵
            .collect { newValue ->
                // value가 변경될 때만 실행되는 로직
                when (newValue) {
                    BottomNavItem.Wiki -> {
                        navController.navigateToWikiBaseRoute()
                    }

                    BottomNavItem.Free -> {
                        navController.navigateToFreeBoardBaseRoute()
                    }

                    BottomNavItem.MyPage -> {
                        navController.navigateToMyProfileBaseRoute()
                    }
                }
            }
    }

    Box {
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            NavHost(
                navController = navController,
                startDestination = Landing,
                modifier = Modifier.weight(1f),
            ) {
                landingNavGraph {
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
                    navController = navController,
                )

                navigation<MainNavigationRoute>(
                    startDestination = MyProfileBaseRoute,
                ) {
                    myProfileNavGraph(
                        onNavigateUp = {
                            navController.navigateUp()
                        },
                        onNavigateToMyProfileCreateProfile = {
                            navController.navigateToMyProfileCreateProfile()
                        },
                        onNavigateToMyProfileAddProfile = {
                            navController.navigateToMyProfileAddProfile()
                        },
                        onNavigateToSettings = {
                            navController.navigateToSettings()
                        },
                        onNavigateToMyPosts = {
                            navController.navigateToMyPosts()
                        },
                        onNavigateToMyProfileModifyProfile = {
                            navController.navigateToMyProfileModifyProfile()
                        },
                        onNavigateToMyProfileManageProfile = {
                            profileId, profileType->
                            navController.navigateToMyProfileManageProfile(profileId = profileId, profileType = profileType)
                        },
                    )
                    freeNavGraph(
                        onNavigateUp = {
                            navController.navigateUp()
                        },
                        onNavigateToFreeBoardWriteFreeBoard = {
                            navController.navigateToFreeBoardWriteScreen()
                        },
                        navController = navController,
                    )
                    wikiNavGraph()
                }
                settingsNavGraph(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onDismissLogoutDialog = {
                        navController.navigateUp()
                    },
                    onLogout = {
                        authManager.logout()
                    },
                    onOpenLogoutDialog = {
                        navController.navigateToLogoutDialog()
                    },
                    onNavigateToMemberLeave = {
                        navController.navigateToMemberLeave()
                    },
                )
                myPostsNavGraph(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onNavigateToFreeBoardDetail = {
                        navController.navigateToFreeBoardDetailScreen(it.toString())
                    },
                )
            }
        }
        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = shouldShowBottomBar,
            enter =
                fadeIn(
                    // NavHost의 기본 애니메이션 duration과 easing 매칭
                    animationSpec =
                        tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing,
                        ),
                ),
            exit =
                fadeOut(
                    animationSpec =
                        tween(
                            durationMillis = 300,
                            easing = FastOutSlowInEasing,
                        ),
                ),
        ) {
            LanPetBottomNavBar(
                selectedBottomNavItem = navItem,
                bottomNavItemList =
                    listOf(
                        BottomNavItem.Wiki,
                        BottomNavItem.Free,
                        BottomNavItem.MyPage,
                    ),
                onItemSelect = { item ->
                    Timber.d("selected bottom nav item: $item")
                    navItem = item
                },
            )
        }
    }
}

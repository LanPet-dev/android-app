package com.lanpet.core.navigation

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import androidx.navigation.navigation
import com.lanpet.core.auth.BuildConfig
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.widget.BottomNavItem
import com.lanpet.core.common.widget.LanPetBottomNavBar
import com.lanpet.feature.auth.navigation.authNavGraph
import com.lanpet.feature.auth.navigation.navigateToLoginScreen
import com.lanpet.feature.landing.navigation.landingNavGraph
import com.lanpet.feature.myposts.navigation.myPostsNavGraph
import com.lanpet.feature.myposts.navigation.navigateToMyPosts
import com.lanpet.feature.settings.navigation.navigateToLogoutDialog
import com.lanpet.feature.settings.navigation.navigateToMemberLeave
import com.lanpet.feature.settings.navigation.navigateToMemberLeaveComplete
import com.lanpet.feature.settings.navigation.navigateToSettings
import com.lanpet.feature.settings.navigation.settingsNavGraph
import com.lanpet.feature.splash.SplashScreen
import com.lanpet.feature.splash.navigation.Splash
import com.lanpet.free.navigation.FreeBoard
import com.lanpet.free.navigation.freeNavGraph
import com.lanpet.free.navigation.navigateToFreeBoardBaseRoute
import com.lanpet.free.navigation.navigateToFreeBoardCommentDetailScreen
import com.lanpet.free.navigation.navigateToFreeBoardDetailScreen
import com.lanpet.free.navigation.navigateToFreeBoardWriteScreen
import com.lanpet.myprofile.navigation.MyProfile
import com.lanpet.myprofile.navigation.MyProfileBaseRoute
import com.lanpet.myprofile.navigation.myProfileNavGraph
import com.lanpet.myprofile.navigation.navigateToMyProfileAddProfile
import com.lanpet.myprofile.navigation.navigateToMyProfileAddProfileEntry
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
import timber.log.Timber

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current
    val authManager = LocalAuthManager.current

    BackHandler {
        authManager.finish()
        (context as Activity).finishAffinity()
    }

    if (BuildConfig.BUILD_TYPE == "debug") {
        DestinationLoggerHandler(navController)
    }

    val authState = authManager.authState.collectAsStateWithLifecycle()

    // Handling navigation by AuthState
    rememberNavigationHandler(navController, authState.value)

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    var shouldShowBottomBar by remember {
        mutableStateOf(false)
    }

    var navItem by rememberSaveable {
        mutableStateOf<BottomNavItem?>(null)
    }

    Box {
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
        ) {
            NavHost(
                navController = navController,
                startDestination = Splash,
                modifier = Modifier.weight(1f),
            ) {
                composable<Splash> {
                    SplashScreen(navController = navController)
                }

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
                            navController.navigateToMyProfileAddProfile(
                                profileType = it,
                            )
                        },
                        onNavigateToSettings = {
                            navController.navigateToSettings()
                        },
                        onNavigateToMyPosts = {
                            navController.navigateToMyPosts(
                                profileId = it,
                            )
                        },
                        onNavigateToMyProfileModifyProfile = {
                            navController.navigateToMyProfileModifyProfile()
                        },
                        onNavigateToMyProfileManageProfile = { profileId, profileType ->
                            navController.navigateToMyProfileManageProfile(
                                profileId = profileId,
                                profileType = profileType,
                            )
                        },
                        onNavigateToMyProfileAddProfileEntry = {
                            navController.navigateToMyProfileAddProfileEntry()
                        },
                    )
                    freeNavGraph(
                        onNavigateUp = {
                            navController.navigateUp()
                        },
                        onNavigateToFreeBoardWriteFreeBoard = {
                            navController.navigateToFreeBoardWriteScreen()
                        },
                        onNavigateToFreeBoardDetail = { postId, profileId, nickname, navOptions ->
                            navController.navigateToFreeBoardDetailScreen(
                                postId,
                                profileId,
                                nickname,
                                navOptions,
                            )
                        },
                        onNavigateToFreeBoardCommentDetail = { postId, freeBoardComment ->
                            navController.navigateToFreeBoardCommentDetailScreen(
                                postId = postId,
                                freeBoardComment = freeBoardComment,
                            )
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
                    onNavigateToMemberLeaveComplete = {
                        navController.navigateToMemberLeaveComplete()
                    },
                )
                myPostsNavGraph(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onNavigateToFreeBoardDetail = { postId, profileId, nickname ->
                        navController.navigateToFreeBoardDetailScreen(postId, profileId, nickname)
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
            if (navItem != null) {
                LanPetBottomNavBar(
                    selectedBottomNavItem = navItem!!,
                    bottomNavItemList =
                        listOf(
                            BottomNavItem.Wiki,
                            BottomNavItem.Free,
                            BottomNavItem.MyPage,
                        ),
                    onItemSelect = { item ->
                        Timber.d("selected bottom nav item: $item")
                        navItem = item

                        when (item) {
                            BottomNavItem.Wiki -> {
                                navController.navigateToWikiBaseRoute(
                                    topLevelDestinationNavOptions(),
                                )
                            }

                            BottomNavItem.Free -> {
                                navController.navigateToFreeBoardBaseRoute(
                                    topLevelDestinationNavOptions(),
                                )
                            }

                            BottomNavItem.MyPage -> {
                                navController.navigateToMyProfileBaseRoute(
                                    topLevelDestinationNavOptions(),
                                )
                            }
                        }
                    },
                )
            }
        }
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

                else -> false
            }
    }
}

private fun topLevelDestinationNavOptions(): NavOptions =
    navOptions {
        popUpTo(MainNavigationRoute) {
            saveState = true
            inclusive = true
        }

        launchSingleTop = true
        restoreState = true
    }

@Composable
private fun DestinationLoggerHandler(navController: NavHostController) {
    @SuppressLint("RestrictedApi")
    val destinationChangedListener: (NavController, NavDestination, Bundle?) -> Unit =
        { controller, _, _ ->
            val stack =
                controller.currentBackStack.value

            // 각 항목을 "->"로 구분하여 보기 좋게 출력
            val stackLog = stack.joinToString("\n") { "  $${it.destination.id}" }
            Timber.i("previousBackStackEntry: ${controller.previousBackStackEntry}\n")
            Timber.i("currentBackStackEntry: ${controller.currentBackStackEntry}\n")
            Timber.d("Navigation Stack:\n====================\n$stackLog\n====================")
        }

    DisposableEffect(Unit) {
        navController.addOnDestinationChangedListener(destinationChangedListener)
        onDispose {
            navController.removeOnDestinationChangedListener(destinationChangedListener)
        }
    }
}

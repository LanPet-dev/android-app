package com.lanpet.core.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.widget.BottomNavItem
import com.lanpet.core.common.widget.LanPetBottomNavBar
import com.lanpet.core.designsystem.theme.VioletColor
import com.lanpet.core.designsystem.theme.WhiteColor
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.repository.AuthRepository
import com.lanpet.domain.repository.LandingRepository
import com.lanpet.feature.auth.navigation.Login
import com.lanpet.feature.auth.navigation.authNavGraph
import com.lanpet.feature.auth.navigation.navigateToLoginScreen
import com.lanpet.feature.landing.navigation.Landing
import com.lanpet.feature.landing.navigation.landingNavGraph
import com.lanpet.feature.myposts.navigation.myPostsNavGraph
import com.lanpet.feature.myposts.navigation.navigateToMyPosts
import com.lanpet.feature.settings.navigation.navigateToLogoutDialog
import com.lanpet.feature.settings.navigation.navigateToMemberLeave
import com.lanpet.feature.settings.navigation.navigateToMemberLeaveComplete
import com.lanpet.feature.settings.navigation.navigateToSettings
import com.lanpet.feature.settings.navigation.settingsNavGraph
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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import timber.log.Timber
import javax.inject.Inject

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

    val topLevelDestinationNavOptions =
        navOptions {
            popUpTo(MainNavigationRoute(BottomNavItem.Wiki)) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
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
                        navController.navigateToWikiBaseRoute(topLevelDestinationNavOptions)
                    }

                    BottomNavItem.Free -> {
                        navController.navigateToFreeBoardBaseRoute(topLevelDestinationNavOptions)
                    }

                    BottomNavItem.MyPage -> {
                        navController.navigateToMyProfileBaseRoute(topLevelDestinationNavOptions)
                    }
                }
            }
    }
}

@Serializable
data object Splash

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    val startDestination by splashViewModel.startDestination.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (startDestination != null) {
            startDestination?.let {
                Timber.d("startDestination: $it")
                navController.navigate(it)
            }
        }
    }

    Column(
        modifier =
            modifier.then(Modifier).fillMaxSize().background(
                color = VioletColor.Violet500,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            "랜펫",
            style =
                MaterialTheme.customTypography().title2SemiBoldMulti.copy(
                    color = WhiteColor.White,
                    fontSize = 64.sp,
                ),
        )
    }
}

@HiltViewModel
class SplashViewModel
    @Inject
    constructor(
        private val landingRepository: LandingRepository,
        private val authRepository: AuthRepository,
        private val authManager: AuthManager,
    ) : ViewModel() {
        val startDestination: MutableStateFlow<Any?> = MutableStateFlow(null)

        init {
            runBlocking {
                delay(3_000L)

                if (landingRepository.getShouldShowLanding()) {
                    startDestination.value = Landing
                    return@runBlocking
                }

                val token = authRepository.getAuthTokenFromDataStore()

                Timber.i("token: $token")

                if (token != null) {
                    authManager.handleAuthentication(
                        SocialAuthToken(
                            socialAuthType = token.socialAuthType,
                            accessToken = token.accessToken,
                            refreshToken = token.refreshToken,
                            expiresIn = token.expiresIn,
                            expireDateTime = token.expireDateTime,
                        ),
                    )
                    return@runBlocking
                }

                startDestination.value = Login
            }
        }
    }

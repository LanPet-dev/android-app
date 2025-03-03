package com.lanpet.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.manager.CoilManager
import com.lanpet.core.manager.LocalCoilManager
import com.lanpet.core.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var coilManager: CoilManager

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.e("MainActivity onCreate")
        // 사용자가 설정한 SplashScreen 스타일을 기본으로 사용
//        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            CompositionLocalProvider(LocalAuthManager provides authManager) {
                CompositionLocalProvider(LocalCoilManager provides coilManager) {
                    LanPetAppTheme {
                        AppNavigation(
                            navController = navController,
                        )
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        Timber.e("MainActivity onDestroy")
        super.onDestroy()
    }
}

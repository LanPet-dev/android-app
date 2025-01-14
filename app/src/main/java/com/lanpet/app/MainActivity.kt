package com.lanpet.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.manager.CoilManager
import com.lanpet.core.manager.LocalCoilManager
import com.lanpet.core.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var coilManager: CoilManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalAuthManager provides authManager) {
                CompositionLocalProvider(LocalCoilManager provides coilManager) {
                    LanPetAppTheme {
                        AppNavigation()
                    }
                }
            }
        }
    }
}

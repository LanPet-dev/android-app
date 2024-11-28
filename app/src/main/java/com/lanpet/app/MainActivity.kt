package com.lanpet.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.lanpet.core.auth.LocalAuthViewModel
import com.lanpet.core.auth.viewmodel.AuthViewModel
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.navigation.AppNavigation
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CompositionLocalProvider(LocalAuthViewModel provides hiltViewModel<AuthViewModel>()) {
                LanPetAppTheme {
                    AppNavigation()
                }
            }
        }
    }
}

package com.example.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.app.ui.theme.LanPetAppTheme
import com.example.navigation.AppNavigation
import com.example.navigation.AppNavigationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navigationViewModel: AppNavigationViewModel = hiltViewModel()

            LanPetAppTheme {
                AppNavigation(
                    navigationManager = navigationViewModel.navigationManager
                )
            }
        }
    }
}

package com.lanpet.feature.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.VioletColor
import com.lanpet.feature.splash.navigation.Splash
import timber.log.Timber

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = hiltViewModel(),
) {
    val startDestination by splashViewModel.startDestination.collectAsStateWithLifecycle()

    DisposableEffect(startDestination) {
        startDestination?.let {
            navController.navigate(it) {
                popUpTo(Splash) {
                    inclusive = true
                }
            }
        }
        onDispose {
            Timber.d("SplashScreen DisposableEffect onDispose")
        }
    }

    Column(
        modifier =
            modifier.then(Modifier).fillMaxSize().background(
                color = VioletColor.Violet600,
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(id = com.lanpet.core.common.R.drawable.ic_logo),
            contentDescription = "ic_app_logo",
            modifier = Modifier.size(240.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun SplashScreenPreview() {
    LanPetAppTheme {
        SplashScreen(navController = rememberNavController())
    }
}

package com.lanpet.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.lanpet.core.designsystem.theme.VioletColor
import com.lanpet.core.designsystem.theme.WhiteColor
import com.lanpet.core.designsystem.theme.customTypography
import timber.log.Timber

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

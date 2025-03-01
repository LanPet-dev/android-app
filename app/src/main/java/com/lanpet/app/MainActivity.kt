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
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.repository.AuthRepository
import com.lanpet.domain.repository.LandingRepository
import com.lanpet.feature.auth.navigation.Login
import com.lanpet.feature.landing.navigation.Landing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var coilManager: CoilManager

    @Inject
    lateinit var landingRepository: LandingRepository

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            // Start destination
            val startDestination =
                runBlocking {
                    return@runBlocking if (landingRepository.getShouldShowLanding()) {
                        Landing
                    } else {
                        Login
                    }
                }

            // Auto login
            runBlocking {
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
                }
            }

            CompositionLocalProvider(LocalAuthManager provides authManager) {
                CompositionLocalProvider(LocalCoilManager provides coilManager) {
                    LanPetAppTheme {
                        AppNavigation(
                            startDestination = startDestination,
                        )
                    }
                }
            }
        }
    }
}

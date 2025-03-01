package com.lanpet.feature.splash

import androidx.lifecycle.ViewModel
import com.lanpet.core.auth.AuthManager
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.repository.AuthRepository
import com.lanpet.domain.repository.LandingRepository
import com.lanpet.feature.auth.navigation.Login
import com.lanpet.feature.landing.navigation.Landing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

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

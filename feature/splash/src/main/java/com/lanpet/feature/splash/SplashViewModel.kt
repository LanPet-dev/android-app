package com.lanpet.feature.splash

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.auth.AuthManager
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.repository.AuthRepository
import com.lanpet.domain.repository.LandingRepository
import com.lanpet.feature.auth.navigation.Login
import com.lanpet.feature.landing.navigation.Landing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
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
            viewModelScope.launch {
                // Android 12 이하에서는 3초간 스플래시 화면을 보여줍니다.
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                    delay(3_000L)
                }

                if (landingRepository.getShouldShowLanding()) {
                    startDestination.emit(Landing)
                    return@launch
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
                } else {
                    Timber.i("token is null")
                    startDestination.emit(Login)
                }
            }
        }
    }

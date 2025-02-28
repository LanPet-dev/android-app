package com.lanpet.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.manager.CoilManager
import com.lanpet.core.manager.LocalCoilManager
import com.lanpet.core.navigation.AppNavigation
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.model.SocialAuthType
import com.lanpet.domain.repository.LandingRepository
import com.lanpet.feature.auth.navigation.Login
import com.lanpet.feature.landing.navigation.Landing
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var coilManager: CoilManager

    @Inject
    lateinit var landingRepository: LandingRepository

    @Inject
    @Named("AuthDataStore")
    lateinit var authDataStore: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val startDestination =
                runBlocking {
                    return@runBlocking if (landingRepository.getShouldShowLanding()) {
                        Landing
                    } else {
                        Login
                    }
                }
            runBlocking {
                val tokenData = authDataStore.data.first()

                if (tokenData.get(stringPreferencesKey("accessToken")) != null &&
                    tokenData.get(
                        stringPreferencesKey("refreshToken"),
                    ) != null
                ) {
                    authManager.handleAuthentication(
                        SocialAuthToken(
                            socialAuthType = SocialAuthType.GOOGLE,
                            accessToken = tokenData.get(stringPreferencesKey("accessToken")),
                            refreshToken = tokenData.get(stringPreferencesKey("refreshToken")),
                            expiresIn = 1000,
                        ),
                    )
                }
            }

            Timber.d("startDestination: $startDestination")

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

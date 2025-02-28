package com.lanpet.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.lanpet.data.dto.RefreshTokenResponse
import com.lanpet.data.dto.TokenResponse
import com.lanpet.data.dto.toSocialAuthToken
import com.lanpet.data.service.AuthService
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.model.SocialAuthType
import com.lanpet.domain.model.toSocialAuthType
import com.lanpet.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.Date
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authService: AuthService,
        @Named("AuthDataStore") private val authDataStore: DataStore<Preferences>,
    ) : AuthRepository {
        override suspend fun getAuthToken(authCode: String): Flow<SocialAuthToken> =
            flow {
                val response: TokenResponse =
                    authService.getTokens(
                        grantType = "authorization_code",
                        code = authCode,
                        clientId = "1me4f6pjgepjiur33u3j5dj3r7",
                        redirectUri = "auth://lanpet.com",
                    )

                emit(
                    response.toSocialAuthToken(
                        socialAuthType = SocialAuthType.GOOGLE,
                    ),
                )
            }.flowOn(Dispatchers.IO)

        override suspend fun refreshAuthToken(refreshToken: String): Flow<SocialAuthToken> =
            flow<SocialAuthToken> {
                val response: RefreshTokenResponse =
                    authService.refreshTokens(
                        grantType = "refresh_token",
                        refreshToken = refreshToken,
                        clientId = "1me4f6pjgepjiur33u3j5dj3r7",
                    )

                val tokenResponse: TokenResponse =
                    TokenResponse(
                        accessToken = response.accessToken,
                        expiresIn = response.expiresIn,
                        refreshToken = refreshToken,
                        tokenType = response.tokenType,
                        idToken = response.idToken,
                    )

                emit(
                    tokenResponse.toSocialAuthToken(
                        socialAuthType = SocialAuthType.GOOGLE,
                    ),
                )
            }

        override suspend fun saveAuthToken(socialAuthToken: SocialAuthToken): SocialAuthToken {
            assert(socialAuthToken.accessToken != null && socialAuthToken.refreshToken != null)

            authDataStore.edit { preferences ->
                preferences[accessTokenKey] = socialAuthToken.accessToken!!
                preferences[refreshTokenKey] = socialAuthToken.refreshToken!!
                preferences[expiresInKey] = socialAuthToken.expiresIn.toString()
                preferences[expireDateTimeKey] = socialAuthToken.expireDateTime.toString()
                preferences[socialAuthTypeKey] = socialAuthToken.socialAuthType.name
            }

            return socialAuthToken
        }

        override suspend fun deleteAuthToken(): Boolean {
            try {
                authDataStore.edit { preferences ->
                    preferences.remove(accessTokenKey)
                    preferences.remove(refreshTokenKey)
                    preferences.remove(expiresInKey)
                    preferences.remove(expireDateTimeKey)
                    preferences.remove(socialAuthTypeKey)
                }

                return true
            } catch (e: Exception) {
                Timber.e(e)
                return false
            }
        }

        override suspend fun getAuthTokenFromDataStore(): SocialAuthToken? {
            try {
                val preferences = authDataStore.data.firstOrNull()

                return if (preferences != null) {
                    if (preferences[accessTokenKey] == null || preferences[refreshTokenKey] == null) {
                        return null
                    }

                    SocialAuthToken(
                        socialAuthType = preferences[socialAuthTypeKey]!!.toSocialAuthType(),
                        accessToken = preferences[accessTokenKey],
                        refreshToken = preferences[refreshTokenKey],
                        expiresIn = preferences[expiresInKey]?.toInt(),
                        expireDateTime =
                            preferences[expireDateTimeKey]?.let { date ->
                                Date(date)
                            } ?: Date(),
                    )
                } else {
                    null
                }
            } catch (e: Exception) {
                Timber.e(e)
                return null
            }
        }

        companion object {
            val accessTokenKey = stringPreferencesKey("accessToken")
            val refreshTokenKey = stringPreferencesKey("refreshToken")
            val expiresInKey = stringPreferencesKey("expiresIn")
            val expireDateTimeKey = stringPreferencesKey("expireDateTime")
            val socialAuthTypeKey = stringPreferencesKey("socialAuthType")
        }
    }

package com.lanpet.data.repository

import com.lanpet.data.dto.RefreshTokenResponse
import com.lanpet.data.dto.TokenResponse
import com.lanpet.data.dto.toSocialAuthToken
import com.lanpet.data.service.AuthService
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.model.SocialAuthType
import com.lanpet.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl
    @Inject
    constructor(
        private val authService: AuthService,
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
    }

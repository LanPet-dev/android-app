package com.lanpet.data.repository

import com.lanpet.data.dto.TokenResponse
import com.lanpet.data.dto.toSocialAuthToken
import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.model.SocialAuthType
import com.lanpet.domain.repository.AuthRepository
import com.lanpet.data.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun getAuthToken(authCode: String): Flow<SocialAuthToken> {
        return flow {
            val response: TokenResponse = authService.getTokens(
                grantType = "authorization_code",
                code = authCode,
                clientId = "1me4f6pjgepjiur33u3j5dj3r7",
                redirectUri = "auth://lanpet.com"
            )

            emit(
                response.toSocialAuthToken(
                    socialAuthType = SocialAuthType.GOOGLE
                )
            )
        }.flowOn(Dispatchers.IO)
    }
}
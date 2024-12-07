package com.lanpet.data.repository

import com.example.dto.TokenResponse
import com.example.dto.toSocialAuthToken
import com.example.model.SocialAuthToken
import com.example.model.SocialAuthType
import com.example.repository.AuthRepository
import com.lanpet.service.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService
) : AuthRepository {
    override suspend fun getAuthToken(authCode: String): Result<SocialAuthToken> {
        return try {
            val response: TokenResponse = authService.getTokens(
                grantType = "authorization_code",
                code = authCode,
                clientId = "1me4f6pjgepjiur33u3j5dj3r7",
                redirectUri = "auth://lanpet.com"
            )

            Result.success(
                response.toSocialAuthToken(
                    socialAuthType = SocialAuthType.GOOGLE
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
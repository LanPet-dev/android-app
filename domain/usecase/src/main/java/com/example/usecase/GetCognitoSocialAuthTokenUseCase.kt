package com.example.usecase

import androidx.annotation.Keep
import com.example.model.SocialAuthToken
import com.example.repository.AuthRepository
import javax.inject.Inject

class GetCognitoSocialAuthTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(authCode: String): Result<SocialAuthToken> = authRepository.getAuthToken(authCode)
}
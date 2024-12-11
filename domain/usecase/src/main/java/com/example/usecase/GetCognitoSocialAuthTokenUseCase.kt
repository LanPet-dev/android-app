package com.example.usecase

import com.example.model.SocialAuthToken
import com.example.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCognitoSocialAuthTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(authCode: String): Flow<SocialAuthToken> =
        authRepository.getAuthToken(authCode)
}
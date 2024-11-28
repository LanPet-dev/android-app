package com.example.usecase

import com.example.repository.AuthRepository
import javax.inject.Inject

class GetCognitoSocialAuthTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(authCode: String) = authRepository.getAuthToken(authCode)
}
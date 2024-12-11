package com.lanpet.domain.usecase

import com.lanpet.domain.model.SocialAuthToken
import com.lanpet.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCognitoSocialAuthTokenUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(authCode: String): Flow<SocialAuthToken> =
        authRepository.getAuthToken(authCode)
}
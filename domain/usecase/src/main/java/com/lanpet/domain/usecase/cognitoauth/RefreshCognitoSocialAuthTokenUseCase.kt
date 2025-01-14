package com.lanpet.domain.usecase.cognitoauth

import com.lanpet.domain.repository.AuthRepository
import javax.inject.Inject

class RefreshCognitoSocialAuthTokenUseCase
    @Inject
    constructor(
        private val authRepository: AuthRepository,
    ) {
        suspend operator fun invoke(refreshToken: String) = authRepository.refreshAuthToken(refreshToken)
    }

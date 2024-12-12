package com.lanpet.domain.repository

import com.lanpet.domain.model.SocialAuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getAuthToken(authCode: String): Flow<SocialAuthToken>
}
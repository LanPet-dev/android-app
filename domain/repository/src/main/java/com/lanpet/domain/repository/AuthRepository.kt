package com.lanpet.domain.repository

import com.lanpet.domain.model.SocialAuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getAuthToken(authCode: String): Flow<SocialAuthToken>

    suspend fun refreshAuthToken(refreshToken: String): Flow<SocialAuthToken>

    suspend fun saveAuthToken(socialAuthToken: SocialAuthToken): SocialAuthToken

    suspend fun deleteAuthToken(): Boolean

    suspend fun getAuthTokenFromDataStore(): SocialAuthToken?
}

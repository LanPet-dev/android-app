package com.example.repository

import com.example.model.SocialAuthToken
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getAuthToken(authCode: String): Flow<SocialAuthToken>
}
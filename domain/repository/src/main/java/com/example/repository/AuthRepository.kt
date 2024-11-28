package com.example.repository

import com.example.model.SocialAuthToken

interface AuthRepository {
    suspend fun getAuthToken(authCode: String): Result<SocialAuthToken>
}
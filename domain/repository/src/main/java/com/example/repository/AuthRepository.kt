package com.example.repository

import androidx.annotation.Keep
import com.example.model.SocialAuthToken

@Keep
interface AuthRepository {
    suspend fun getAuthToken(authCode: String): Result<SocialAuthToken>
}
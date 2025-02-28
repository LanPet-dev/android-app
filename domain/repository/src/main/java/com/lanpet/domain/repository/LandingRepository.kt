package com.lanpet.domain.repository

interface LandingRepository {
    suspend fun getShouldShowLanding(): Boolean

    suspend fun saveShouldShowLanding(shouldShowLanding: Boolean): Boolean
}
package com.lanpet.domain.repository

import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.UserProfile
import com.lanpet.domain.model.profile.UserProfileDetail
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    // returns id
    suspend fun registerPetProfile(petProfileCreate: PetProfileCreate): Flow<String>

    // returns id
    suspend fun registerButlerProfile(manProfileCreate: ManProfileCreate): Flow<String>

    suspend fun getProfiles(): Flow<List<UserProfile>>

    suspend fun getProfile(id: String): Flow<UserProfileDetail>

    suspend fun updateProfile(
        id: String,
        userProfile: UserProfile,
    ): Flow<Boolean>

    suspend fun deleteProfile(id: String): Flow<Boolean>
}

package com.lanpet.data.repository

import com.lanpet.data.dto.RegisterManProfileRequest
import com.lanpet.data.dto.RegisterPetProfileRequest
import com.lanpet.data.dto.toDomain
import com.lanpet.data.service.ProfileApiService
import com.lanpet.data.service.localdb.AuthDatabase
import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.UserProfile
import com.lanpet.domain.model.profile.UserProfileDetail
import com.lanpet.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val profileApiService: ProfileApiService,
        private val authDatabase: AuthDatabase,
    ) : ProfileRepository {
        override suspend fun registerPetProfile(petProfileCreate: PetProfileCreate): Flow<String> =
            flow {
                val request = RegisterPetProfileRequest.fromDomain(petProfileCreate)
                val res = profileApiService.createPetProfile(request)
                emit(res.id)
            }.flowOn(Dispatchers.IO)

        override suspend fun registerButlerProfile(manProfileCreate: ManProfileCreate): Flow<String> =
            flow {
                val res =
                    profileApiService.createManProfile(
                        RegisterManProfileRequest.fromDomain(manProfileCreate),
                    )
                emit(res.id)
            }.flowOn(Dispatchers.IO)

        override suspend fun getProfiles(): Flow<List<UserProfile>> =
            flow {
                val res = profileApiService.getProfileList()
                emit(res.toDomain())
            }.flowOn(Dispatchers.IO)

        override suspend fun getProfile(id: String): Flow<UserProfileDetail> =
            flow {
                val res = profileApiService.getProfileDetail(id)
                emit(res.toDomain())
            }.flowOn(Dispatchers.IO)

        override suspend fun deleteProfile(id: String): Flow<Boolean> {
            TODO("Not yet implemented")
        }

        override suspend fun checkNicknameDuplicated(nickname: String): Flow<Boolean> =
            flow {
                val res = profileApiService.checkNicknameDuplicated(nickname)
                emit(!res.isExist)
            }.flowOn(Dispatchers.IO)

        override suspend fun getDefaultProfile(accountId: String): Flow<String?> =
            flow {
                val profile = authDatabase.authDao().getByAccountId(accountId)
                Timber.d("Get default profile success $profile")
                emit(profile?.profileId)
            }.flowOn(Dispatchers.IO)

        override suspend fun setDefaultProfile(
            accountId: String,
            profileId: String,
        ): Flow<Boolean> =
            flow {
                try {
                    authDatabase.authDao().upsert(
                        profileId = profileId,
                        accountId = accountId,
                    )
                    Timber.d("Set default profile success $profileId $accountId")
                    emit(true)
                } catch (e: Exception) {
                    emit(false)
                }
            }.flowOn(Dispatchers.IO)
    }

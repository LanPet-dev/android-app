package com.lanpet.data.repository

import com.lanpet.data.dto.RegisterManProfileRequest
import com.lanpet.data.dto.RegisterPetProfileRequest
import com.lanpet.data.dto.toDomain
import com.lanpet.data.service.ProfileApiService
import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.UserProfile
import com.lanpet.domain.repository.ProfileRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepositoryImpl
    @Inject
    constructor(
        private val profileApiService: ProfileApiService,
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

        override suspend fun getProfile(id: String): Flow<UserProfile> {
            throw NotImplementedError("Not implemented")
//            flow {
//                val res = profileApiService.getProfileDetail(id)
//                emit(res.toDomain())
//            }.flowOn(Dispatchers.IO)
        }
    }

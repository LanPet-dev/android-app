package com.lanpet.domain.repository

import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.model.ManProfile
import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.model.PetProfile
import com.lanpet.domain.model.UserProfile
import com.lanpet.domain.model.profile.UserProfileDetail
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    /**
     * 반려동물 프로파일을 등록합니다.
     */
    suspend fun registerPetProfile(petProfileCreate: PetProfileCreate): Flow<String>

    /**
     * 집사 프로파일을 등록합니다.
     */
    suspend fun registerButlerProfile(manProfileCreate: ManProfileCreate): Flow<String>

    /**
     * 반려동물 프로파일을 수정합니다.
     */
    suspend fun modifyPetProfile(
        profileId: String,
        petProfile: PetProfile,
    ): Flow<Boolean>

    /**
     * 집사 프로파일을 수정합니다.
     */
    suspend fun modifyButlerProfile(
        profileId: String,
        manProfile: ManProfile,
    ): Flow<Boolean>

    /**
     * 현재 유저의 모든 프로파일 정보를 가져옵니다.
     */
    suspend fun getProfiles(): Flow<List<UserProfile>>

    /**
     * 현재 유저의 특정 프로파일 id 에 해당하는 프로파일 세부 정보를 가져옵니다.
     */
    suspend fun getProfile(id: String): Flow<UserProfileDetail>

    /**
     * 현재 유저의 특정 프로파일 id 에 해당하는 프로파일을 삭제합니다.
     */
    suspend fun deleteProfile(id: String): Flow<Boolean>

    /**
     * nickname 에 대한 중복체크를 합니다.
     */
    suspend fun checkNicknameDuplicated(nickname: String): Flow<Boolean>

    /**
     * 현재 유저의 기본 프로파일을 가져옵니다.
     */
    suspend fun getDefaultProfile(accountId: String): Flow<String?>

    /**
     * 현재 유저의 기본 프로파일을 설정합니다.
     */
    suspend fun setDefaultProfile(
        accountId: String,
        profileId: String,
    ): Flow<Boolean>
}

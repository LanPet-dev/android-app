package com.lanpet.domain.usecase.profile

import com.lanpet.domain.model.profile.UserProfileDetail
import com.lanpet.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileDetailUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend operator fun invoke(profileId: String): Flow<UserProfileDetail> = profileRepository.getProfile(profileId)
    }

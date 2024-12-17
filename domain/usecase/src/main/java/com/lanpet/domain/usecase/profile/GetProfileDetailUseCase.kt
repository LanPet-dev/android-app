package com.lanpet.domain.usecase.profile

import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileDetailUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend fun execute(profileId: String) = profileRepository.getProfile(profileId)
    }

package com.lanpet.domain.usecase.profile

import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class GetAllProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend fun invoke() = profileRepository.getProfiles()
    }

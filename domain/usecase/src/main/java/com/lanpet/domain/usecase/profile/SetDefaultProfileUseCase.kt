package com.lanpet.domain.usecase.profile

import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class SetDefaultProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend operator fun invoke(profileId: String) = profileRepository.setDefaultProfile(profileId)
    }

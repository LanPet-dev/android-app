package com.lanpet.domain.usecase.profile

import com.lanpet.domain.model.ManProfile
import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class ModifyManProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend operator fun invoke(
            profileId: String,
            manProfile: ManProfile,
        ) = profileRepository.modifyButlerProfile(
            profileId = profileId,
            manProfile = manProfile,
        )
    }

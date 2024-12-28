package com.lanpet.domain.usecase.profile

import com.lanpet.domain.model.PetProfile
import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class ModifyPetProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend operator fun invoke(
            profileId: String,
            petProfile: PetProfile,
        ) = profileRepository.modifyPetProfile(
            profileId = profileId,
            petProfile = petProfile,
        )
    }

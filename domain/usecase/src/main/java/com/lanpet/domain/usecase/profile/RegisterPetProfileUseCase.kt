package com.lanpet.domain.usecase.profile

import com.lanpet.domain.model.PetProfileCreate
import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class RegisterPetProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend operator fun invoke(petProfileCreate: PetProfileCreate) = profileRepository.registerPetProfile(petProfileCreate)
    }

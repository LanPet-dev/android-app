package com.lanpet.domain.usecase.profile

import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class RegisterManProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend fun invoke(manProfileCreate: ManProfileCreate) = profileRepository.registerButlerProfile(manProfileCreate)
    }

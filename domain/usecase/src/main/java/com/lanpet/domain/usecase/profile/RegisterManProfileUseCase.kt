package com.lanpet.domain.usecase.profile

import com.lanpet.domain.model.ManProfileCreate
import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class RegisterManProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend fun execute(manProfileCreate: ManProfileCreate) = profileRepository.registerButlerProfile(manProfileCreate)
    }

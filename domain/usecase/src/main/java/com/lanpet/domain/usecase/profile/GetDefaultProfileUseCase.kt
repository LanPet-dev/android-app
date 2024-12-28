package com.lanpet.domain.usecase.profile

import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class GetDefaultProfileUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend operator fun invoke(accountId: String) = profileRepository.getDefaultProfile(accountId)
    }

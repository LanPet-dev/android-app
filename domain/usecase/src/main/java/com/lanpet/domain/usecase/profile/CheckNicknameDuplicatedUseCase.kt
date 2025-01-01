package com.lanpet.domain.usecase.profile

import com.lanpet.domain.repository.ProfileRepository
import javax.inject.Inject

class CheckNicknameDuplicatedUseCase
    @Inject
    constructor(
        private val profileRepository: ProfileRepository,
    ) {
        suspend operator fun invoke(nickname: String) = profileRepository.checkNicknameDuplicated(nickname)
    }

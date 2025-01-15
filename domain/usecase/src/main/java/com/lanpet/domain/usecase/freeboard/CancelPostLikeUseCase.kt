package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class CancelPostLikeUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            sarangbangId: String,
            profileId: String,
        ) = freeBoardRepository.cancelPostLike(sarangbangId, profileId)
    }

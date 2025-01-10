package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.FreeBoardPostLike
import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class DoPostLikeUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            sarangbangId: String,
            freeBoardPostLike: FreeBoardPostLike,
        ) = freeBoardRepository.doPostLike(sarangbangId, freeBoardPostLike)
    }

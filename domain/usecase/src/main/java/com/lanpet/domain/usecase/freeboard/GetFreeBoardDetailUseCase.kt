package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class GetFreeBoardDetailUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            postId: String,
            profileId: String,
        ) = freeBoardRepository.getFreeBoardDetail(postId, profileId)
    }

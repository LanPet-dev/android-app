package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.FreeBoardPostCreate
import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class CreateFreeBoardPostUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(freeBoardPostCreate: FreeBoardPostCreate) = freeBoardRepository.createFreeBoardPost(freeBoardPostCreate)
    }

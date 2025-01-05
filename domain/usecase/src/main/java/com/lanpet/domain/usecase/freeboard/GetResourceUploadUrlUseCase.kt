package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class GetResourceUploadUrlUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
    operator fun invoke(sarangbangId: String, size: Int)
        = freeBoardRepository.getResourceUploadUrl(sarangbangId, size)
}

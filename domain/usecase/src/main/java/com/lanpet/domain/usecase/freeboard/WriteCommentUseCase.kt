package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.free.FreeBoardWriteComment
import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class WriteCommentUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            sarangbangId: String,
            writeComment: FreeBoardWriteComment,
        ) = freeBoardRepository.writeComment(sarangbangId, writeComment)
    }

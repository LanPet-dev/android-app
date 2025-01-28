package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.free.FreeBoardWriteComment
import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class WriteSubCommentUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            postId: String,
            commentId: String,
            writeComment: FreeBoardWriteComment,
        ) = freeBoardRepository.writeSubComment(postId, commentId, writeComment)
    }

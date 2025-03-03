package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class DeleteFreeBoardCommentUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            postId: String,
            commentId: String,
        ) = freeBoardRepository.deleteComment(postId, commentId)
    }

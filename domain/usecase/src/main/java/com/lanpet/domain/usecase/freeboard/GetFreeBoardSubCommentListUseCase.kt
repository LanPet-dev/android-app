package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class GetFreeBoardSubCommentListUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            postId: String,
            commentId: String,
            cursor: String?,
            size: Int?,
            direction: CursorDirection?,
        ) = freeBoardRepository.getFreeBoardSubCommentList(postId, commentId, size, cursor, direction)
    }

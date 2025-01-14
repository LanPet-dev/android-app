package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class GetFreeBoardCommentListUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            postId: String,
            cursor: String?,
            size: Int?,
            direction: CursorDirection?,
        ) = freeBoardRepository
            .getFreeBoardCommentList(
                postId,
                cursor = cursor,
                size = size,
                direction = direction,
            )
    }

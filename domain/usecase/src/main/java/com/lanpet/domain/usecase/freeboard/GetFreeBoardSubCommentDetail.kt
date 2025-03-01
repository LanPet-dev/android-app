package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.free.FreeBoardSubComment
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFreeBoardSubCommentDetail
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            postId: String,
            commentId: String,
            subCommentId: String,
        ): Flow<FreeBoardSubComment> =
            freeBoardRepository.getFreeBoardSubCommentDetail(
                postId,
                commentId,
                subCommentId,
            )
    }

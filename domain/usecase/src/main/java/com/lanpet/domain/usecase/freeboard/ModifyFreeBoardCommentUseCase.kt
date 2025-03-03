package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapMerge
import javax.inject.Inject

class ModifyFreeBoardCommentUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        operator fun invoke(
            postId: String,
            commentId: String,
            comment: String,
        ): Flow<FreeBoardComment> =
            freeBoardRepository
                .modifyComment(postId, commentId, comment)
                .flatMapMerge { newCommentId ->
                    freeBoardRepository.getCommentDetail(postId, newCommentId)
                }
    }

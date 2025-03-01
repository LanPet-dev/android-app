package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.free.FreeBoardWriteComment
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapMerge
import javax.inject.Inject

class WriteSubCommentUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
        private val getFreeBoardSubCommentDetail: GetFreeBoardSubCommentDetail,
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        operator fun invoke(
            postId: String,
            commentId: String,
            writeComment: FreeBoardWriteComment,
        ) = freeBoardRepository
            .writeSubComment(postId, commentId, writeComment)
            .flatMapMerge { subCommentId ->
                getFreeBoardSubCommentDetail(postId, commentId, subCommentId)
            }
    }

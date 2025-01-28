package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.PaginationData
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetFreeBoardCommentListUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        @OptIn(ExperimentalCoroutinesApi::class)
        operator fun invoke(
            postId: String,
            cursor: String?,
            size: Int?,
            direction: CursorDirection?,
        ): Flow<PaginationData<List<FreeBoardComment>>> =
            freeBoardRepository
                .getFreeBoardCommentList(
                    postId,
                    cursor = cursor,
                    size = size,
                    direction = direction,
                ).flatMapLatest { commentData ->
                    if (commentData.data.isEmpty()) {
                        return@flatMapLatest flowOf(commentData)
                    }


                    flow {
                        val updatedComments = commentData.data.map { comment ->
                            val res =
                                freeBoardRepository
                                    .getFreeBoardSubCommentList(
                                        postId,
                                        comment.id,
                                        size = 10,
                                        cursor = null,
                                        direction = CursorDirection.NEXT,
                                    ).flatMapLatest {
                                        flowOf(
                                            comment.copy(subComments = it.data),
                                        )
                                    }.first()

                            comment.copy(subComments = res.subComments)
                        }

                        emit(commentData.copy(data = updatedComments))
                    }
                }
    }

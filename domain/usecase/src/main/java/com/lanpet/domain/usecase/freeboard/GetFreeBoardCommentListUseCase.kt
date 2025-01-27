package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.PaginationData
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
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
                ).flatMapLatest {
                    val subCommentFlows =
                        it.data.map { comment ->
                            freeBoardRepository.getFreeBoardSubCommentList(
                                postId,
                                comment.id,
                                size = size,
                                cursor = cursor,
                                direction = direction,
                            )
                        }

                    combine(subCommentFlows) { subComments ->
                        val comments =
                            it.data.map { comment ->
                                val subComment = subComments.firstOrNull { it.data.isNotEmpty() }
                                comment.copy(subComments = subComment?.data ?: emptyList())
                            }
                        it.copy(data = comments)
                    }
                }
    }

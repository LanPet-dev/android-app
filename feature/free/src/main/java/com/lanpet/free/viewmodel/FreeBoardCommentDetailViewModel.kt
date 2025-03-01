package com.lanpet.free.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.auth.AuthManager
import com.lanpet.core.common.safeScopedCall
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardSubComment
import com.lanpet.domain.model.free.FreeBoardWriteComment
import com.lanpet.domain.usecase.freeboard.GetFreeBoardSubCommentListUseCase
import com.lanpet.domain.usecase.freeboard.WriteSubCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class FreeBoardCommentDetailViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val getFreeBoardSubCommentListUseCase: GetFreeBoardSubCommentListUseCase,
        private val writeSubCommentUseCase: WriteSubCommentUseCase,
        private val authManager: AuthManager,
    ) : ViewModel() {
        private val postId: String =
            savedStateHandle.get<String>("postId") ?: throw IllegalArgumentException("postId is null")
        private val freeBoardComment: FreeBoardComment =
            savedStateHandle
                .get<String>("freeBoardComment")
                ?.let { Json.decodeFromString<FreeBoardComment>(it) }
                ?: throw IllegalArgumentException("freeBoardComment is null")

        private var subCommentPagingState = CursorPagingState()

        private val _event = MutableSharedFlow<CommentDetailEvent?>()
        val event = _event.asSharedFlow()

        private val _singleCommentUiState =
            MutableStateFlow<SingleCommentUiState>(SingleCommentUiState.Loading)
        val singleCommentUiState = _singleCommentUiState.asStateFlow()

        val commentInput = MutableStateFlow("")

        fun updateCommentInput(comment: String) {
            commentInput.value = comment
        }

        fun writeSubComment() {
            val comment = commentInput.value
            if (comment.isEmpty()) return

            writeSubCommentUseCase(
                postId = postId,
                commentId = freeBoardComment.id,
                writeComment =
                    FreeBoardWriteComment(
                        profileId = authManager.defaultUserProfile.value.id,
                        comment = comment,
                    ),
            ).safeScopedCall(
                scope = viewModelScope,
                block = { subComment ->
                    commentInput.value = ""
                    _event.emit(CommentDetailEvent.WriteSubCommentSuccess())
                    updateSubCommentCache(subComment)
                },
                onFailure = {
                    _event.emit(CommentDetailEvent.WriteSubCommentFail())
                },
            )
        }

        private fun refreshSubComment() {
            _singleCommentUiState.update {
                SingleCommentUiState.Loading
            }
            subCommentPagingState = CursorPagingState()
            getSubComment()
        }

        fun getSubComment() {
            if (!subCommentPagingState.hasNext) return

            getFreeBoardSubCommentListUseCase(
                postId = postId,
                commentId = freeBoardComment.id,
                cursor = subCommentPagingState.cursor,
                size = subCommentPagingState.size,
                direction = subCommentPagingState.direction,
            ).safeScopedCall(
                scope = viewModelScope,
                block = { subCommentList ->
                    _singleCommentUiState.update { state ->
                        when (state) {
                            is SingleCommentUiState.Success -> {
                                SingleCommentUiState.Success(
                                    comment =
                                        state.comment.copy(
                                            subComments =
                                                state.comment.subComments + subCommentList.data,
                                        ),
                                    hasMoreSubComment = subCommentList.paginationInfo.hasNext,
                                )
                            }

                            else -> {
                                SingleCommentUiState.Success(
                                    comment = freeBoardComment.copy(subComments = subCommentList.data),
                                    hasMoreSubComment = subCommentList.paginationInfo.hasNext,
                                )
                            }
                        }
                    }

                    subCommentPagingState =
                        subCommentPagingState.copy(
                            cursor = subCommentList.paginationInfo.nextCursor,
                            hasNext = subCommentList.paginationInfo.hasNext,
                        )
                },
            )
        }

        private fun updateSubCommentCache(cache: FreeBoardSubComment) {
            if (subCommentPagingState.hasNext) return

            _singleCommentUiState.update {
                when (it) {
                    is SingleCommentUiState.Success -> {
                        SingleCommentUiState.Success(
                            comment =
                                it.comment.copy(
                                    subComments = it.comment.subComments + cache,
                                ),
                        )
                    }

                    else ->
                        SingleCommentUiState.Success(
                            comment = freeBoardComment.copy(subComments = listOf(cache)),
                        )
                }
            }
        }

        init {
            getSubComment()
        }
    }

sealed interface SingleCommentUiState {
    data class Success(
        val comment: FreeBoardComment,
        val hasMoreSubComment: Boolean = false,
    ) : SingleCommentUiState

    data class Error(
        val message: String,
    ) : SingleCommentUiState

    data object Loading : SingleCommentUiState
}

sealed interface CommentDetailEvent {
    data class WriteSubCommentSuccess(
        val message: String? = null,
    ) : CommentDetailEvent

    data class WriteSubCommentFail(
        val message: String? = null,
    ) : CommentDetailEvent
}

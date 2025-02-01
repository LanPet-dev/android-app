package com.lanpet.free.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.auth.AuthManager
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardWriteComment
import com.lanpet.domain.usecase.freeboard.GetFreeBoardSubCommentListUseCase
import com.lanpet.domain.usecase.freeboard.WriteSubCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

            viewModelScope.launch {
                runCatching {
                    writeSubCommentUseCase(
                        postId = postId,
                        commentId = freeBoardComment.id,
                        writeComment =
                            FreeBoardWriteComment(
                                profileId = authManager.defaultUserProfile.value.id,
                                comment = commentInput.value,
                            ),
                    ).first()

                    commentInput.value = ""
                    _event.emit(CommentDetailEvent.WriteSubCommentSuccess())
                }.onFailure {
                    _event.emit(CommentDetailEvent.WriteSubCommentFail())
                }
            }
        }

        fun getSubComment() {
            if (!subCommentPagingState.hasNext) return

            viewModelScope.launch {
                runCatching {
                    getFreeBoardSubCommentListUseCase(
                        postId = postId,
                        commentId = freeBoardComment.id,
                        cursor = subCommentPagingState.cursor,
                        size = subCommentPagingState.size,
                        direction = subCommentPagingState.direction,
                    ).collect {
                        _singleCommentUiState.update { state ->
                            when (state) {
                                is SingleCommentUiState.Success -> {
                                    SingleCommentUiState.Success(
                                        comment =
                                            state.comment.copy(
                                                subComments =
                                                    state.comment.subComments + it.data,
                                            ),
                                    )
                                }

                                else -> {
                                    SingleCommentUiState.Success(
                                        comment = freeBoardComment.copy(subComments = it.data),
                                    )
                                }
                            }
                        }

                        subCommentPagingState =
                            subCommentPagingState.copy(
                                cursor = it.paginationInfo.nextCursor,
                                hasNext = it.paginationInfo.hasNext,
                            )
                    }
                }.onFailure { e ->
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

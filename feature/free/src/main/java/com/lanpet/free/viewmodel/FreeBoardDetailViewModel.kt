package com.lanpet.free.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.common.safeScopedCall
import com.lanpet.core.common.toUtcDateString
import com.lanpet.domain.model.Profile
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardPostDetail
import com.lanpet.domain.model.free.FreeBoardWriteComment
import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.usecase.freeboard.DeleteFreeBoardCommentUseCase
import com.lanpet.domain.usecase.freeboard.GetFreeBoardCommentListUseCase
import com.lanpet.domain.usecase.freeboard.GetFreeBoardDetailUseCase
import com.lanpet.domain.usecase.freeboard.ModifyFreeBoardCommentUseCase
import com.lanpet.domain.usecase.freeboard.WriteCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

// TODO("Satoshi"): refactor: Separate detail post and comments by using different viewmodel
@HiltViewModel
class FreeBoardDetailViewModel
    @Inject
    constructor(
        private val getFreeBoardDetailUseCase: GetFreeBoardDetailUseCase,
        private val getFreeBoardCommentListUseCase: GetFreeBoardCommentListUseCase,
        private val writeCommentUseCase: WriteCommentUseCase,
        private val deleteFreeBoardCommentUseCase: DeleteFreeBoardCommentUseCase,
        private val modifyFreeBoardCommentUseCase: ModifyFreeBoardCommentUseCase,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val postId =
            savedStateHandle.get<String>("postId")
                ?: throw IllegalArgumentException("postId is required")
        private val profileId =
            savedStateHandle.get<String>("profileId")
                ?: throw IllegalArgumentException("profileId is required")
        private val nickname =
            savedStateHandle.get<String>("nickname")
                ?: throw IllegalArgumentException("nickname is required for owner check")

        private val detailState: MutableStateFlow<DetailState> =
            MutableStateFlow<DetailState>(DetailState.Initial)
        private val commentsState: MutableStateFlow<CommentsState> =
            MutableStateFlow<CommentsState>(CommentsState.Initial)

        private val _uiEvent = MutableSharedFlow<FreeBoardDetailEvent>()
        val uiEvent = _uiEvent.asSharedFlow()

        // UI에서 observe할 combined state
        val uiState =
            combine(detailState, commentsState) { detailState, commentsState ->
                when {
                    detailState is DetailState.Error -> FreeBoardDetailState.Error(detailState.message)
                    commentsState is CommentsState.Error -> FreeBoardDetailState.Error(commentsState.message)
                    detailState is DetailState.Success && commentsState is CommentsState.Success -> {
                        FreeBoardDetailState.Success(
                            postDetail = detailState.postDetail,
                            comments = commentsState.comments,
                            canLoadMoreComments = commentsState.cursorPagingState.hasNext,
                            isOwner = detailState.postDetail.writer == nickname,
                        )
                    }

                    else -> FreeBoardDetailState.Loading
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FreeBoardDetailState.Loading,
            )

        private fun init() {
            viewModelScope.launch {
                runCatching {
                    coroutineScope {
                        launch {
                            fetchDetail()
                        }
                        launch {
                            fetchComments()
                        }
                    }
                }.onFailure { e ->
                    detailState.value = DetailState.Error(e.message ?: "Failed to fetch detail")
                }
            }
        }

        fun likePost() {
            if (detailState.value !is DetailState.Success) return
            detailState.value =
                (detailState.value as DetailState.Success).copy(
                    postDetail = (detailState.value as DetailState.Success).postDetail.like(),
                )
        }

        fun dislikePost() {
            if (detailState.value !is DetailState.Success) return
            detailState.value =
                (detailState.value as DetailState.Success).copy(
                    postDetail = (detailState.value as DetailState.Success).postDetail.dislike(),
                )
        }

        fun writeComment(
            postId: String,
            profileId: String,
            profile: Profile,
            comment: String,
        ) {
            writeCommentUseCase(postId, FreeBoardWriteComment(profileId, comment)).safeScopedCall(
                scope = viewModelScope,
                block = {
                    updateCommentCache(
                        FreeBoardComment(
                            it,
                            profile,
                            comment,
                            Date().toUtcDateString(),
                        ),
                    )
                    _uiEvent.emit(FreeBoardDetailEvent.WriteCommentSuccess)
                },
                onFailure = {
                    _uiEvent.emit(FreeBoardDetailEvent.WriteCommentFail)
                },
            )
        }

        fun refreshComments() {
            commentsState.value = CommentsState.Initial
            fetchComments()
        }

        private fun fetchDetail() {
            detailState.value = DetailState.Loading

            getFreeBoardDetailUseCase(postId, profileId).safeScopedCall(
                scope = viewModelScope,
                block = { postDetail ->
                    detailState.value = DetailState.Success(postDetail)
                },
                onFailure = {
                    detailState.value = DetailState.Error("Failed to fetch detail")
                },
            )
        }

        fun fetchComments() {
            val pagingState =
                when (commentsState.value) {
                    is CommentsState.Error ->
                        CursorPagingState(
                            size = 10,
                            direction = CursorDirection.NEXT,
                        )

                    CommentsState.Initial ->
                        CursorPagingState(
                            size = 10,
                            direction = CursorDirection.NEXT,
                        )

                    CommentsState.Loading ->
                        CursorPagingState(
                            size = 10,
                            direction = CursorDirection.NEXT,
                        )

                    is CommentsState.Success -> (commentsState.value as CommentsState.Success).cursorPagingState
                }

            if (!pagingState.hasNext) return

            getFreeBoardCommentListUseCase(
                postId,
                cursor = pagingState.cursor,
                size = pagingState.size,
                direction = pagingState.direction,
            ).safeScopedCall(
                scope = viewModelScope,
                block = { res ->
                    when (commentsState.value) {
                        is CommentsState.Success -> {
                            commentsState.value =
                                CommentsState.Success(
                                    comments = (commentsState.value as CommentsState.Success).comments + res.data,
                                    cursorPagingState =
                                        CursorPagingState(
                                            hasNext = res.paginationInfo.hasNext,
                                            cursor = res.paginationInfo.nextCursor,
                                            size = 10,
                                            direction = CursorDirection.NEXT,
                                        ),
                                )
                        }

                        else -> {
                            commentsState.value =
                                CommentsState.Success(
                                    comments = res.data,
                                    cursorPagingState =
                                        CursorPagingState(
                                            hasNext = res.paginationInfo.hasNext,
                                            cursor = res.paginationInfo.nextCursor,
                                            size = 10,
                                            direction = CursorDirection.NEXT,
                                        ),
                                )
                        }
                    }
                },
                onFailure = {
                    commentsState.value = CommentsState.Error("Failed to fetch comments")
                },
            )
        }

        fun deleteComment(
            postId: String,
            commentId: String,
        ) {
            deleteFreeBoardCommentUseCase(postId, commentId).safeScopedCall(
                scope = viewModelScope,
                block = {
                    when (val state = commentsState.value) {
                        is CommentsState.Success -> {
                            commentsState.update {
                                state.copy(
                                    comments = state.comments.filter { it.id != commentId },
                                )
                            }
                        }

                        else -> Unit
                    }
                },
                onFailure = {},
            )
        }

        fun modifyComment(
            postId: String,
            commentId: String,
            content: String,
        ) {
            modifyFreeBoardCommentUseCase(postId, commentId, content).safeScopedCall(
                scope = viewModelScope,
                block = {
                    when (val state = commentsState.value) {
                        is CommentsState.Success -> {
                            commentsState.update {
                                state.copy(
                                    comments =
                                        state.comments.map {
                                            if (it.id == commentId) {
                                                it.copy(comment = content)
                                            } else {
                                                it
                                            }
                                        },
                                )
                            }
                        }

                        else -> Unit
                    }
                },
                onFailure = {},
            )
        }

        private fun updateCommentCache(comment: FreeBoardComment) {
            if (commentsState.value !is CommentsState.Success) return

            if ((commentsState.value as CommentsState.Success).cursorPagingState.hasNext) return

            commentsState.value =
                CommentsState.Success(
                    comments =
                        commentsState.value.let {
                            (it as CommentsState.Success).comments + comment
                        },
                    cursorPagingState = (commentsState.value as CommentsState.Success).cursorPagingState,
                )
        }

        init {
            init()
        }
    }

@Stable
private sealed class DetailState {
    data object Loading : DetailState()

    data class Success(
        val postDetail: FreeBoardPostDetail,
    ) : DetailState()

    data class Error(
        val message: String,
    ) : DetailState()

    data object Initial : DetailState()
}

@Stable
private sealed class CommentsState {
    data object Loading : CommentsState()

    data class Success(
        val comments: List<FreeBoardComment>,
        val cursorPagingState: CursorPagingState =
            CursorPagingState(
                size = 20,
                direction = CursorDirection.NEXT,
            ),
        val isLoadingMore: Boolean = false,
    ) : CommentsState()

    data class Error(
        val message: String,
    ) : CommentsState()

    data object Initial : CommentsState()
}

@Stable
sealed class FreeBoardDetailState {
    data object Loading : FreeBoardDetailState()

    data class Success(
        val postDetail: FreeBoardPostDetail,
        val comments: List<FreeBoardComment> = emptyList(),
        val canLoadMoreComments: Boolean = true,
        val isOwner: Boolean = false,
    ) : FreeBoardDetailState()

    data class Error(
        val message: String,
    ) : FreeBoardDetailState()
}

@Stable
sealed class FreeBoardDetailEvent {
    data object WriteCommentSuccess : FreeBoardDetailEvent()

    data object WriteCommentFail : FreeBoardDetailEvent()
}

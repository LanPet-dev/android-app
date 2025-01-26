package com.lanpet.free.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.common.toUtcDateString
import com.lanpet.domain.model.Profile
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardPostDetail
import com.lanpet.domain.model.free.FreeBoardWriteComment
import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.usecase.freeboard.CancelPostLikeUseCase
import com.lanpet.domain.usecase.freeboard.DoPostLikeUseCase
import com.lanpet.domain.usecase.freeboard.GetFreeBoardCommentListUseCase
import com.lanpet.domain.usecase.freeboard.GetFreeBoardDetailUseCase
import com.lanpet.domain.usecase.freeboard.WriteCommentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
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
        private val doPostLikeUseCase: DoPostLikeUseCase,
        private val cancelPostLikeUseCase: CancelPostLikeUseCase,
        private val writeCommentUseCase: WriteCommentUseCase,
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

        private val _isProcess = MutableStateFlow(false)
        val isProcess = _isProcess.asStateFlow()

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

        fun init() {
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
            if (_isProcess.value) return
            _isProcess.value = true

            val job =
                viewModelScope.launch {
                    runCatching {
                        writeCommentUseCase(postId, FreeBoardWriteComment(profileId, comment)).collect {
                            _uiEvent.emit(FreeBoardDetailEvent.WriteCommentSuccess)
                        }
                    }.onFailure {
                        _uiEvent.emit(FreeBoardDetailEvent.WriteCommentFail)
                    }.onSuccess {
                        updateCommentCache(
                            FreeBoardComment(
                                "temp",
                                profile,
                                comment,
                                Date().toUtcDateString(),
                            ),
                        )
                    }
                }

            job.invokeOnCompletion { _isProcess.value = false }
        }

        fun refreshComments() {
            commentsState.value = CommentsState.Initial
            fetchComments()
        }

        private fun fetchDetail() {
            detailState.value = DetailState.Loading

            viewModelScope.launch {
                getFreeBoardDetailUseCase(postId, profileId)
                    .catch {
                        detailState.value = DetailState.Error("Failed to fetch detail")
                    }.collect {
                        detailState.value = DetailState.Success(it)
                    }
            }
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

            viewModelScope.launch {
                getFreeBoardCommentListUseCase(
                    postId,
                    cursor = pagingState.cursor,
                    size = pagingState.size,
                    direction = pagingState.direction,
                ).catch {
                    commentsState.value = CommentsState.Error("Failed to fetch comments")
                }.collect {
                    when (commentsState.value) {
                        is CommentsState.Success -> {
                            commentsState.value =
                                CommentsState.Success(
                                    comments = (commentsState.value as CommentsState.Success).comments + it.data,
                                    cursorPagingState =
                                        CursorPagingState(
                                            hasNext = it.paginationInfo.hasNext,
                                            cursor = it.paginationInfo.nextCursor,
                                            size = 10,
                                            direction = CursorDirection.NEXT,
                                        ),
                                )
                        }

                        else ->
                            commentsState.value =
                                CommentsState.Success(
                                    comments = it.data,
                                    cursorPagingState =
                                        CursorPagingState(
                                            hasNext = it.paginationInfo.hasNext,
                                            cursor = it.paginationInfo.nextCursor,
                                            size = 10,
                                            direction = CursorDirection.NEXT,
                                        ),
                                )
                    }
                }
            }
        }

        fun updateCommentCache(comment: FreeBoardComment) {
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

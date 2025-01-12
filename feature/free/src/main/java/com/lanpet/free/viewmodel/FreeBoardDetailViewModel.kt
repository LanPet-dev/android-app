package com.lanpet.free.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardPostDetail
import com.lanpet.domain.model.free.FreeBoardPostLike
import com.lanpet.domain.model.free.FreeBoardWriteComment
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
import timber.log.Timber
import javax.inject.Inject

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
                        )
                    }

                    else -> FreeBoardDetailState.Loading
                }
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FreeBoardDetailState.Loading,
            )

        fun init(
            postId: String,
            profileId: String,
        ) {
            viewModelScope.launch {
                runCatching {
                    coroutineScope {
                        launch {
                            fetchDetail(postId, profileId)
                        }
                        launch {
                            fetchComments(postId)
                        }
                    }
                }.onFailure { e ->
                    detailState.value = DetailState.Error(e.message ?: "Failed to fetch detail")
                }
            }
        }

        fun doLikePost(
            postId: String,
            profileId: String,
        ) {
            if (_isProcess.value) return
            _isProcess.value = true

            viewModelScope.launch {
                runCatching {
                    doPostLikeUseCase(postId, FreeBoardPostLike(profileId)).collect {
                        _isProcess.value = false
                    }
                }.onFailure {
                    _isProcess.value = false
                }
            }
        }

        fun cancelLikePost(
            postId: String,
            profileId: String,
        ) {
            if (_isProcess.value) return
            _isProcess.value = true

            viewModelScope.launch {
                runCatching {
                    cancelPostLikeUseCase(postId, profileId).collect {
                        _isProcess.value = false
                    }
                }.onFailure {
                    _isProcess.value = false
                }
            }
        }

        fun writeComment(
            postId: String,
            profileId: String,
            comment: String,
        ) {
            if (_isProcess.value) return
            _isProcess.value = true

            viewModelScope.launch {
                runCatching {
                    writeCommentUseCase(postId, FreeBoardWriteComment(profileId, comment)).collect {
                        _isProcess.value = false
                        _uiEvent.emit(FreeBoardDetailEvent.WriteCommentSuccess)
                    }
                }.onFailure {
                    _isProcess.value = false
                    _uiEvent.emit(FreeBoardDetailEvent.WriteCommentFail)
                }
            }
        }

        fun refreshComments(postId: String) {
            viewModelScope.launch {
                fetchComments(postId)
            }
        }

        private suspend fun fetchDetail(
            postId: String,
            profileId: String,
        ) {
            detailState.value = DetailState.Loading

            getFreeBoardDetailUseCase(postId, profileId)
                .catch {
                    detailState.value = DetailState.Error("Failed to fetch detail")
                }.collect {
                    detailState.value = DetailState.Success(it)
                }
        }

        private suspend fun fetchComments(postId: String) {
            commentsState.value = CommentsState.Loading

            getFreeBoardCommentListUseCase(postId)
                .catch {
                    commentsState.value = CommentsState.Error("Failed to fetch comments")
                }.collect {
                    commentsState.value = CommentsState.Success(it)
                }
        }

        init {
            val postId = savedStateHandle.get<String>("postId")
            val profileId = savedStateHandle.get<String>("profileId")

            Timber.d("postId: $postId, profileId: $profileId")

            if (postId != null && profileId != null) {
                init(postId, profileId)
            } else {
                detailState.value = DetailState.Error("Failed to fetch detail")
            }
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

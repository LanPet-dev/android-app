package com.lanpet.free.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardPostDetail
import com.lanpet.domain.usecase.freeboard.GetFreeBoardCommentListUseCase
import com.lanpet.domain.usecase.freeboard.GetFreeBoardDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
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
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val detailState: MutableStateFlow<DetailState> =
            MutableStateFlow<DetailState>(DetailState.Initial)
        private val commentsState: MutableStateFlow<CommentsState> =
            MutableStateFlow<CommentsState>(CommentsState.Initial)

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

        fun init(postId: String) {
            viewModelScope.launch {
                coroutineScope {
                    launch {
                        fetchDetail(postId)
                    }
                    launch {
                        fetchComments(postId)
                    }
                }
            }
        }

        fun refreshComments(postId: String) {
            viewModelScope.launch {
                fetchComments(postId)
            }
        }

        private suspend fun fetchDetail(postId: String) {
            detailState.value = DetailState.Loading

            getFreeBoardDetailUseCase(postId)
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
            savedStateHandle.get<String>("postId")?.let {
                Timber.d("postId: $it")
                init(it)
            } ?: run {
                Timber.e("postId is null")
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

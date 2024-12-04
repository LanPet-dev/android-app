package com.lanpet.free.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.FreeBoardComment
import com.example.model.FreeBoardPostDetail
import com.example.model.PetCategory
import com.lanpet.core.common.loremIpsum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FreeBoardDetailViewModel @Inject constructor() : ViewModel() {
    private val _detailState = MutableStateFlow<DetailState>(DetailState.Loading)
    private val _commentsState = MutableStateFlow<CommentsState>(CommentsState.Loading)

    //TODO: UseCase

    // UI에서 observe할 combined state
    val uiState = combine(_detailState, _commentsState) { detailState, commentsState ->
        when {
            detailState is DetailState.Error -> FreeBoardDetailState.Error(detailState.message)
            commentsState is CommentsState.Error -> FreeBoardDetailState.Error(commentsState.message)
            detailState is DetailState.Success && commentsState is CommentsState.Success -> {
                FreeBoardDetailState.Success(
                    postDetail = detailState.postDetail,
                    comments = commentsState.comments
                )
            }

            else -> FreeBoardDetailState.Loading
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = FreeBoardDetailState.Loading
    )

    fun init(postId: Int) {
        fetchDetail(postId)
        fetchComments(postId)
    }

    fun refreshComments(postId: Int) {
        fetchComments(postId)
    }

    private fun fetchDetail(postId: Int) {
        viewModelScope.launch {
            _detailState.value = DetailState.Loading
            try {
                val detail = getFreeBoardDetail(postId)
                _detailState.value = DetailState.Success(detail)
            } catch (e: Exception) {
                _detailState.value = DetailState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun fetchComments(postId: Int) {
        viewModelScope.launch {
            _commentsState.value = CommentsState.Loading
            try {
                val comments = getFreeBoardComments(postId)
                _commentsState.value = CommentsState.Success(comments)
            } catch (e: Exception) {
                _commentsState.value = CommentsState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun getFreeBoardComments(postId: Int): List<FreeBoardComment> {
        // 네트워크 요청
        return listOf(
            FreeBoardComment(
                id = 1,
                content = "댓글1",
                writer = "작성자1",
                writerImage = "https://dummyimage.com/600x400/000/fff",
                createdAt = "2021-08-01T12:34:56+09:00",
                updatedAt = "2021-08-01T12:34:56+09:00",
                freeBoardId = postId,
                likeCount = 10,
                commentCount = 5,
                subComments = listOf(
                    FreeBoardComment(
                        id = 1,
                        content = "대댓글1",
                        writer = "작성자1",
                        writerImage = "https://dummyimage.com/600x400/000/fff",
                        createdAt = "2021-08-01T12:34:56+09:00",
                        updatedAt = "2021-08-01T12:34:56+09:00",
                        freeBoardId = postId,
                        likeCount = 10,
                        commentCount = 5
                    ),
                    FreeBoardComment(
                        id = 2,
                        content = "대댓글2",
                        writer = "작성자2",
                        writerImage = "https://dummyimage.com/600x400/000/fff",
                        createdAt = "2021-08-01T12:34:56+09:00",
                        updatedAt = "2021-08-01T12:34:56+09:00",
                        freeBoardId = postId,
                        likeCount = 10,
                        commentCount = 5
                    )
                )
            ),
            FreeBoardComment(
                id = 2,
                content = "댓글2",
                writer = "작성자2",
                writerImage = "https://dummyimage.com/600x400/000/fff",
                createdAt = "2021-08-01T12:34:56+09:00",
                updatedAt = "2021-08-01T12:34:56+09:00",
                freeBoardId = postId,
                likeCount = 10,
                commentCount = 5
            )
        )
    }

    private fun getFreeBoardDetail(postId: Int): FreeBoardPostDetail {
        // 네트워크 요청
        return FreeBoardPostDetail(
            id = postId,
            title = "제목",
            content = loremIpsum(),
            writer = "작성자",
            writerImage = "https://dummyimage.com/600x400/000/fff",
            createdAt = "2021-08-01T12:34:56+09:00",
            updatedAt = "2021-08-01T12:34:56+09:00",
            likeCount = 10,
            commentCount = 5,
            images = listOf("https://example.com/image1.jpg", "https://example.com/image2.jpg"),
            tags = listOf("태그1", "태그2"),
            petCategory = PetCategory.DOG
        )
    }
}

private sealed class DetailState {
    data object Loading : DetailState()
    data class Success(val postDetail: FreeBoardPostDetail) : DetailState()
    data class Error(val message: String) : DetailState()
}

private sealed class CommentsState {
    data object Loading : CommentsState()
    data class Success(val comments: List<FreeBoardComment>) : CommentsState()
    data class Error(val message: String) : CommentsState()
}

sealed class FreeBoardDetailState {
    data object Loading : FreeBoardDetailState()
    data class Success(
        val postDetail: FreeBoardPostDetail,
        val comments: List<FreeBoardComment> = emptyList()
    ) : FreeBoardDetailState()

    data class Error(val message: String) : FreeBoardDetailState()
}
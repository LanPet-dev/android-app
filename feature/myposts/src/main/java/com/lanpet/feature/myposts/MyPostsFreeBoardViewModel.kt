package com.lanpet.feature.myposts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.common.safeScopedCall
import com.lanpet.domain.model.free.FreeBoardItem
import com.lanpet.domain.model.free.FreeBoardPost
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.usecase.freeboard.GetFreeBoardPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPostsFreeBoardViewModel
    @Inject
    constructor(
        private val getFreeBoardPostListUseCase: GetFreeBoardPostListUseCase,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val profileId =
            savedStateHandle.get<String>("profileId")
                ?: throw IllegalArgumentException("profileId is required")

        private val _uiState =
            MutableStateFlow<MyPostsFreeBoardUiState>(MyPostsFreeBoardUiState.Loading)
        val uiState = _uiState.asStateFlow()

        fun getFreeBoardPostList() {
            if (_uiState.value is MyPostsFreeBoardUiState.Success && !(uiState.value as MyPostsFreeBoardUiState.Success).hasNext) {
                return
            }

            val getFreeBoardPostListRequest = getGetFreeBoardPostListRequest(_uiState.value)

            getFreeBoardPostListUseCase(getFreeBoardPostListRequest).safeScopedCall(
                scope = viewModelScope,
                block = { freeBoardPostList ->
                    handleGetFreeBoardPostListResult(_uiState.value, freeBoardPostList)
                },
                onFailure = {
                    _uiState.value = MyPostsFreeBoardUiState.Error(it.message ?: "Unknown error")
                },
            )
        }

        private fun getGetFreeBoardPostListRequest(currentUiState: MyPostsFreeBoardUiState): GetFreeBoardPostListRequest =
            when (currentUiState) {
                is MyPostsFreeBoardUiState.Success -> currentUiState.getFreeBoardPostListRequest
                else ->
                    GetFreeBoardPostListRequest(
                        cursor = null,
                        size = 10,
                        freeBoardCategoryType = null,
                        direction = CursorDirection.NEXT,
                        profileId = profileId,
                    )
            }

        private fun handleGetFreeBoardPostListResult(
            currentUiState: MyPostsFreeBoardUiState,
            freeBoardPost: FreeBoardPost,
        ) {
            when (currentUiState) {
                is MyPostsFreeBoardUiState.Error -> {
                    _uiState.value =
                        MyPostsFreeBoardUiState.Success(
                            postList = freeBoardPost.items ?: emptyList(),
                            hasNext = freeBoardPost.nextCursor != null,
                            getFreeBoardPostListRequest =
                                GetFreeBoardPostListRequest(
                                    cursor = freeBoardPost.nextCursor,
                                    size = 10,
                                    freeBoardCategoryType = null,
                                    direction = CursorDirection.NEXT,
                                    profileId = profileId,
                                ),
                        )
                }

                MyPostsFreeBoardUiState.Loading -> {
                    _uiState.value =
                        MyPostsFreeBoardUiState.Success(
                            postList = freeBoardPost.items ?: emptyList(),
                            hasNext = freeBoardPost.nextCursor != null,
                            getFreeBoardPostListRequest =
                                GetFreeBoardPostListRequest(
                                    cursor = freeBoardPost.nextCursor,
                                    size = 10,
                                    freeBoardCategoryType = null,
                                    direction = CursorDirection.NEXT,
                                    profileId = profileId,
                                ),
                        )
                }

                is MyPostsFreeBoardUiState.Success -> {
                    _uiState.value =
                        currentUiState.copy(
                            postList = currentUiState.postList + (freeBoardPost.items ?: emptyList()),
                            hasNext = freeBoardPost.nextCursor != null,
                            getFreeBoardPostListRequest =
                                GetFreeBoardPostListRequest(
                                    cursor = freeBoardPost.nextCursor,
                                    size = 10,
                                    freeBoardCategoryType = null,
                                    direction = CursorDirection.NEXT,
                                    profileId = profileId,
                                ),
                        )
                }
            }
        }

        init {
            getFreeBoardPostList()
        }
    }

sealed class MyPostsFreeBoardUiState {
    data object Loading : MyPostsFreeBoardUiState()

    data class Success(
        val postList: List<FreeBoardItem>,
        val hasNext: Boolean = true,
        val getFreeBoardPostListRequest: GetFreeBoardPostListRequest =
            GetFreeBoardPostListRequest(
                cursor = null,
                size = 10,
                freeBoardCategoryType = null,
                direction = CursorDirection.NEXT,
            ),
    ) : MyPostsFreeBoardUiState()

    data class Error(
        val message: String,
    ) : MyPostsFreeBoardUiState()
}

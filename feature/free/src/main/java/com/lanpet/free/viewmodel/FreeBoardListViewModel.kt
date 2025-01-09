package com.lanpet.free.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.FreeBoardItem
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.usecase.freeboard.GetFreeBoardPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FreeBoardListViewModel
    @Inject
    constructor(
        private val getFreeBoardPostListUseCase: GetFreeBoardPostListUseCase,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<FreeBoardListState> =
            MutableStateFlow(FreeBoardListState.Loading())
        val uiState = _uiState.asStateFlow()

        // TODO("Satoshi"): Set UiEvent

        // TODO("Satoshi"): Cursor pagination

        fun loadMore() {
            viewModelScope.launch {
            }
        }

        fun getFreeBoardPostList() {
            viewModelScope.launch {
                _uiState.update { currentState ->
                    when (currentState) {
                        is FreeBoardListState.Success -> {
                            FreeBoardListState.Loading(
                                data = currentState.data,
                                freeBoardPostListRequest = currentState.freeBoardPostListRequest,
                            )
                        }

                        else -> {
                            FreeBoardListState.Loading()
                        }
                    }
                }

                assert(uiState.value is FreeBoardListState.Loading)

                Timber.d("getFreeBoardPostList")

                runCatching {
                    val getFreeBoardPostListRequest =
                        (uiState.value as? FreeBoardListState.Loading)?.freeBoardPostListRequest
                            ?: GetFreeBoardPostListRequest(
                                cursor = null,
                                size = 10,
                                freeBoardCategoryType = null,
                                direction = CursorDirection.NEXT,
                            )
                    Timber.d("getFreeBoardPostListRequest: $getFreeBoardPostListRequest")

                    getFreeBoardPostListUseCase(getFreeBoardPostListRequest).collect {
                        Timber.d("getFreeBoardPostListUseCase.collect: $it")

                        _uiState.update { currentState ->
                            when (currentState) {
                                is FreeBoardListState.Loading -> {
                                    val data: List<FreeBoardItem> =
                                        currentState.data.orEmpty<FreeBoardItem>() + (
                                            it.items
                                                ?: emptyList()
                                        )

                                    if (data.isEmpty()) {
                                        FreeBoardListState.Empty
                                    } else {
                                        val request =
                                            GetFreeBoardPostListRequest(
                                                cursor = it.nextCursor,
                                                size = getFreeBoardPostListRequest.size,
                                                freeBoardCategoryType = getFreeBoardPostListRequest.freeBoardCategoryType,
                                                direction = getFreeBoardPostListRequest.direction,
                                            )
                                        FreeBoardListState.Success(
                                            data = data,
                                            freeBoardPostListRequest = request,
                                        )
                                    }
                                }

                                is FreeBoardListState.Success -> {
                                    val data = currentState.data + (it.items ?: emptyList())
                                    val request =
                                        GetFreeBoardPostListRequest(
                                            cursor = it.nextCursor,
                                            size = getFreeBoardPostListRequest.size,
                                            freeBoardCategoryType = getFreeBoardPostListRequest.freeBoardCategoryType,
                                            direction = getFreeBoardPostListRequest.direction,
                                        )
                                    FreeBoardListState.Success(
                                        data = data,
                                        request,
                                    )
                                }

                                else -> {
                                    val request =
                                        GetFreeBoardPostListRequest(
                                            cursor = it.nextCursor,
                                            size = getFreeBoardPostListRequest.size,
                                            freeBoardCategoryType = getFreeBoardPostListRequest.freeBoardCategoryType,
                                            direction = getFreeBoardPostListRequest.direction,
                                        )

                                    if (it.items.isNullOrEmpty()) {
                                        FreeBoardListState.Empty
                                    } else {
                                        FreeBoardListState.Success(data = it.items!!, request)
                                    }
                                }
                            }
                        }
                    }
                }.onFailure {
                    Timber.e(it)
                    _uiState.value = FreeBoardListState.Error(it.message)
                }
            }
        }
    }

@Stable
sealed class FreeBoardListState {
    @Stable
    data class Loading(
        val data: List<FreeBoardItem>? = null,
        val freeBoardPostListRequest: GetFreeBoardPostListRequest? =
            GetFreeBoardPostListRequest(
                cursor = null,
                size = 10,
                freeBoardCategoryType = null,
                direction = CursorDirection.NEXT,
            ),
    ) : FreeBoardListState()

    @Stable
    data object Empty : FreeBoardListState()

    @Stable
    data class Success(
        val data: List<FreeBoardItem>,
        val freeBoardPostListRequest: GetFreeBoardPostListRequest =
            GetFreeBoardPostListRequest(
                cursor = null,
                size = 10,
                freeBoardCategoryType = null,
                direction = CursorDirection.NEXT,
            ),
    ) : FreeBoardListState()

    @Stable
    data class Error(
        val message: String?,
    ) : FreeBoardListState()
}

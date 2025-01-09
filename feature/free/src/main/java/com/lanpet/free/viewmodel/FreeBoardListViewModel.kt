package com.lanpet.free.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.FreeBoardCategoryType
import com.lanpet.domain.model.FreeBoardItem
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.usecase.freeboard.GetFreeBoardPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
            MutableStateFlow(FreeBoardListState.Initial())
        val uiState = _uiState.asStateFlow()

        private val _selectedCategory =
            MutableStateFlow<FreeBoardCategoryType>(FreeBoardCategoryType.ALL)
        val selectedCategoryFlow = _selectedCategory.asStateFlow()

        private val _isProcess = MutableStateFlow(false)
        val isProcess = _isProcess.asStateFlow()

        // TODO("Satoshi"): Set UiEvent

        // TODO("Satoshi"): Cursor pagination

        fun setCategory(
            category: FreeBoardCategoryType,
            forceRefresh: Boolean = false,
        ) {
            if (_selectedCategory.value == category) return
            _selectedCategory.value = category

            if (forceRefresh) {
                refresh()
            }
        }

        fun refresh() {
            _uiState.value = FreeBoardListState.Initial()
            getFreeBoardPostList()
        }

        @VisibleForTesting
        fun getPagingRequest(): GetFreeBoardPostListRequest =
            when (val currentUiState = _uiState.value) {
                is FreeBoardListState.Success -> {
                    currentUiState.freeBoardPostListRequest
                }

                else -> {
                    GetFreeBoardPostListRequest(
                        cursor = null,
                        size = 10,
                        freeBoardCategoryType = _selectedCategory.value,
                        direction = CursorDirection.NEXT,
                    )
                }
            }

        @VisibleForTesting
        fun handleGetFreeBoardPostList(
            currentUiState: FreeBoardListState,
            data: FreeBoardPost,
        ): FreeBoardListState {
            val request =
                GetFreeBoardPostListRequest(
                    cursor = data.nextCursor,
                    size = SIZE,
                    freeBoardCategoryType = _selectedCategory.value,
                    direction = CursorDirection.NEXT,
                )

            when (currentUiState) {
                is FreeBoardListState.Error -> {
                    return FreeBoardListState.Success(
                        data = data.items.orEmpty(),
                        freeBoardPostListRequest =
                        request,
                    )
                }

                is FreeBoardListState.Initial -> {
                    return FreeBoardListState.Success(
                        data = data.items.orEmpty(),
                        freeBoardPostListRequest =
                        request,
                    )
                }

                is FreeBoardListState.Success -> {
                    if(!currentUiState.hasNextData){
                        return currentUiState
                    }

                    return FreeBoardListState.Success(
                        data = currentUiState.data + data.items.orEmpty(),
                        freeBoardPostListRequest =
                        request,
                        hasNextData = data.items?.isNotEmpty() == true,
                    )
                }
            }
        }

        fun getFreeBoardPostList() {
            if (_isProcess.value) return
            _isProcess.value = true
            runCatching {
                val getFreeBoardPostListRequest = getPagingRequest()

                viewModelScope
                    .launch {
                        delay(1000)
                        getFreeBoardPostListUseCase(getFreeBoardPostListRequest).collect { data ->
                            _uiState.update { currentState ->
                                handleGetFreeBoardPostList(currentState, data)
                            }

                            _isProcess.value = false
                        }
                    }
            }.onFailure {
                Timber.e(it)
                _isProcess.value = false
                _uiState.value = FreeBoardListState.Error(it.message)
            }
        }

        init {
            getFreeBoardPostList()
        }

        companion object {
            const val SIZE = 10
        }
    }

@Stable
sealed class FreeBoardListState {
    @Stable
    data class Initial(
        val freeBoardPostListRequest: GetFreeBoardPostListRequest =
            GetFreeBoardPostListRequest(
                cursor = null,
                size = 10,
                freeBoardCategoryType = null,
                direction = CursorDirection.NEXT,
            ),
    ) : FreeBoardListState()

    @Stable
    data class Success(
        val data: List<FreeBoardItem> = emptyList(),
        val freeBoardPostListRequest: GetFreeBoardPostListRequest =
            GetFreeBoardPostListRequest(
                cursor = null,
                size = 10,
                freeBoardCategoryType = null,
                direction = CursorDirection.NEXT,
            ),
        val hasNextData: Boolean = true,
    ) : FreeBoardListState()

    @Stable
    data class Error(
        val message: String?,
    ) : FreeBoardListState()
}

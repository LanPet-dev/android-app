package com.lanpet.free.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.domain.model.free.FreeBoardItem
import com.lanpet.domain.model.free.FreeBoardPost
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.model.pagination.CursorDirection
import com.lanpet.domain.usecase.freeboard.GetFreeBoardPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FreeBoardListViewModel
    @Inject
    constructor(
        private val getFreeBoardPostListUseCase: GetFreeBoardPostListUseCase,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<FreeBoardListState> =
            MutableStateFlow(FreeBoardListState.Initial)
        val uiState = _uiState.asStateFlow()

        private var _cursorPagingState: CursorPagingState = CursorPagingState()

        private val _selectedCategory =
            MutableStateFlow<FreeBoardCategoryType>(FreeBoardCategoryType.ALL)
        val selectedCategoryFlow = _selectedCategory.asStateFlow()

        private val _isProcess = MutableStateFlow(Mutex(false))
        val isProcess = _isProcess.asStateFlow()

        private var getPostListJob: Job? = null

        // TODO("Satoshi"): Set UiEvent

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
            _uiState.value = FreeBoardListState.Initial
            _cursorPagingState = CursorPagingState()
            getFreeBoardPostList()
        }

        @VisibleForTesting
        fun getPagingRequest(): GetFreeBoardPostListRequest =
            GetFreeBoardPostListRequest(
                cursor = _cursorPagingState.cursor,
                size = _cursorPagingState.size,
                freeBoardCategoryType = _selectedCategory.value,
                direction = _cursorPagingState.direction,
            )

        @VisibleForTesting
        fun handleGetFreeBoardPostList(
            currentUiState: FreeBoardListState,
            data: FreeBoardPost,
        ): FreeBoardListState {
            _cursorPagingState =
                _cursorPagingState.copy(
                    cursor = data.nextCursor,
                    hasNext = data.nextCursor != null,
                )

            when (currentUiState) {
                is FreeBoardListState.Error -> {
                    return FreeBoardListState.Success(
                        data = data.items.orEmpty(),
                    )
                }

                is FreeBoardListState.Initial -> {
                    return FreeBoardListState.Success(
                        data = data.items.orEmpty(),
                    )
                }

                is FreeBoardListState.Success -> {
                    return FreeBoardListState.Success(
                        data = currentUiState.data + data.items.orEmpty(),
                    )
                }
            }
        }

        fun getFreeBoardPostList() {
            if (!_cursorPagingState.hasNext) {
                return
            }

            if (getPostListJob != null && getPostListJob?.isActive == true) {
                getPostListJob!!.cancel()
            }

            getPostListJob =
                viewModelScope
                    .launch {
                        runCatching {
                            val getFreeBoardPostListRequest = getPagingRequest()

                            getFreeBoardPostListUseCase(
                                getFreeBoardPostListRequest,
                            ).collect { data ->
                                _uiState.update { currentState ->
                                    handleGetFreeBoardPostList(currentState, data)
                                }
                            }
                        }.onFailure {
                            Timber.e(it)
                            when (it) {
                                is CancellationException -> return@onFailure
                                is Exception -> _uiState.value = FreeBoardListState.Error(it.message)
                            }
                        }
                    }
        }

        init {
            getFreeBoardPostList()
        }
    }

@Stable
sealed class FreeBoardListState {
    @Stable
    data object Initial : FreeBoardListState()

    @Stable
    data class Success(
        val data: List<FreeBoardItem> = emptyList(),
    ) : FreeBoardListState()

    @Stable
    data class Error(
        val message: String?,
    ) : FreeBoardListState()
}

@Stable
data class CursorPagingState(
    val cursor: String? = null,
    val size: Int = 10,
    val direction: CursorDirection = CursorDirection.NEXT,
    val hasNext: Boolean = true,
)

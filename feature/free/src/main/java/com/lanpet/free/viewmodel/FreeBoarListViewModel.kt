package com.lanpet.free.viewmodel

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.usecase.freeboard.GetFreeBoardPostListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FreeBoarListViewModel
    @Inject
    constructor(
        private val getFreeBoardPostListUseCase: GetFreeBoardPostListUseCase,
    ) : ViewModel() {
        private val _uiState: MutableStateFlow<FreeBoardListState> =
            MutableStateFlow(FreeBoardListState.Loading)
        val uiState = _uiState.asStateFlow()

        // TODO("Satoshi"): Set UiEvent

        fun getFreeBoardPostList() {
            viewModelScope.launch {
                runCatching {
                    getFreeBoardPostListUseCase().collect {
                        _uiState.value =
                            if (it.isEmpty()) {
                                FreeBoardListState.Empty
                            } else {
                                FreeBoardListState.Success(
                                    (uiState.value as FreeBoardListState.Success).data + it,
                                )
                            }
                    }
                }.onFailure {
                    _uiState.value = FreeBoardListState.Error(it.message)
                }
            }
        }
    }

@Stable
sealed class FreeBoardListState {
    @Stable
    data object Loading : FreeBoardListState()

    @Stable
    data object Empty : FreeBoardListState()

    @Stable
    data class Success(
        val data: List<FreeBoardPost>,
    ) : FreeBoardListState()

    @Stable
    data class Error(
        val message: String?,
    ) : FreeBoardListState()
}

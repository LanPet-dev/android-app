package com.lanpet.free.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.free.FreeBoardPostLike
import com.lanpet.domain.usecase.freeboard.CancelPostLikeUseCase
import com.lanpet.domain.usecase.freeboard.DoPostLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

@HiltViewModel
class FreeBoardLikesViewModel
    @Inject
    constructor(
        private val doPostLikeUseCase: DoPostLikeUseCase,
        private val cancelPostLikeUseCase: CancelPostLikeUseCase,
        savedStateHandle: SavedStateHandle,
    ) : ViewModel() {
        private val sarangbangId =
            savedStateHandle.get<String>("postId")
                ?: throw IllegalArgumentException("postId is required")
        private val profileId =
            savedStateHandle.get<String>("profileId")
                ?: throw IllegalArgumentException("profileId is required")

        private val _uiEvent = MutableSharedFlow<FreeBoardLikeEvent>()
        val uiEvent = _uiEvent.asSharedFlow()

        private val isProcess = Mutex(false)

        fun doPostLike() {
            viewModelScope.launch {
                isProcess.withLock {
                    runCatching {
                        val freeBoardPostLike = FreeBoardPostLike(profileId = profileId)
                        doPostLikeUseCase(
                            sarangbangId = sarangbangId,
                            freeBoardPostLike = freeBoardPostLike,
                        ).collect {
                            _uiEvent.emit(
                                FreeBoardLikeEvent.Success(
                                    isLike = true,
                                ),
                            )
                        }
                    }.onFailure {
                        _uiEvent.emit(FreeBoardLikeEvent.Error)
                    }
                }
            }
        }

        fun cancelPostLike() {
            viewModelScope.launch {
                isProcess.withLock {
                    runCatching {
                        cancelPostLikeUseCase(
                            sarangbangId = sarangbangId,
                            profileId = profileId,
                        ).collect {
                            _uiEvent.emit(
                                FreeBoardLikeEvent.Success(
                                    isLike = false,
                                ),
                            )
                        }
                    }.onFailure {
                        _uiEvent.emit(FreeBoardLikeEvent.Error)
                    }
                }
            }
        }
    }

sealed class FreeBoardLikeEvent {
    data class Success(
        val isLike: Boolean,
    ) : FreeBoardLikeEvent()

    data object Error : FreeBoardLikeEvent()
}

package com.lanpet.free.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.common.safeScopedCall
import com.lanpet.domain.model.free.FreeBoardPostLike
import com.lanpet.domain.usecase.freeboard.CancelPostLikeUseCase
import com.lanpet.domain.usecase.freeboard.DoPostLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
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

        private var isProcess = false

        fun doPostLike() {
            if (isProcess) return
            isProcess = true

            val freeBoardPostLike = FreeBoardPostLike(profileId = profileId)

            doPostLikeUseCase(
                sarangbangId = sarangbangId,
                freeBoardPostLike = freeBoardPostLike,
            ).safeScopedCall(
                scope = viewModelScope,
                block = {
                    _uiEvent.emit(
                        FreeBoardLikeEvent.Success(
                            isLike = true,
                        ),
                    )
                },
                onFailure = {
                    _uiEvent.emit(FreeBoardLikeEvent.Error)
                },
                onComplete = {
                    isProcess = false
                },
            )
        }

        fun cancelPostLike() {
            if (isProcess) return
            isProcess = true

            cancelPostLikeUseCase(
                sarangbangId = sarangbangId,
                profileId = profileId,
            ).safeScopedCall(
                scope = viewModelScope,
                block = {
                    _uiEvent.emit(
                        FreeBoardLikeEvent.Success(
                            isLike = false,
                        ),
                    )
                },
                onFailure = {
                    _uiEvent.emit(FreeBoardLikeEvent.Error)
                },
                onComplete = {
                    isProcess = false
                },
            )
        }
    }

sealed class FreeBoardLikeEvent {
    data class Success(
        val isLike: Boolean,
    ) : FreeBoardLikeEvent()

    data object Error : FreeBoardLikeEvent()
}

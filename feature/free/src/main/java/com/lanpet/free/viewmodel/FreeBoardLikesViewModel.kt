package com.lanpet.free.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.model.free.FreeBoardPostLike
import com.lanpet.domain.usecase.freeboard.CancelPostLikeUseCase
import com.lanpet.domain.usecase.freeboard.DoPostLikeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FreeBoardLikesViewModel
    @Inject
    constructor(
        private val doPostLikeUseCase: DoPostLikeUseCase,
        private val cancelPostLikeUseCase: CancelPostLikeUseCase,
    ) : ViewModel() {
        private val _uiEvent = MutableSharedFlow<Boolean>()
        val uiEvent = _uiEvent.asSharedFlow()

        fun doPostLike(
            sarangbangId: String,
            profileId: String,
        ) {
            viewModelScope.launch {
                runCatching {
                    val freeBoardPostLike = FreeBoardPostLike(profileId = profileId)
                    doPostLikeUseCase(
                        sarangbangId = sarangbangId,
                        freeBoardPostLike = freeBoardPostLike,
                    ).collect {
                        _uiEvent.emit(true)
                    }
                }.onFailure {
                    _uiEvent.emit(false)
                }
            }
        }

        fun cancelPostLike(
            sarangbangId: String,
            profileId: String,
        ) {
            viewModelScope.launch {
                runCatching {
                    cancelPostLikeUseCase(
                        sarangbangId = sarangbangId,
                        profileId = profileId,
                    ).collect {
                        _uiEvent.emit(true)
                    }
                }.onFailure {
                    _uiEvent.emit(false)
                }
            }
        }
    }

package com.lanpet.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.usecase.account.LeaveMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MemberLeaveViewModel
    @Inject
    constructor(
        private val leaveMemberUseCase: LeaveMemberUseCase,
    ) : ViewModel() {
        private val _leaveState = MutableSharedFlow<MemberLeaveState>()
        val leaveState: SharedFlow<MemberLeaveState> = _leaveState.asSharedFlow()

        fun leaveMember() {
            viewModelScope.launch {
                try {
                    _leaveState.emit(MemberLeaveState.Loading)
                    leaveMemberUseCase().collect {
                        if (it) {
                            _leaveState.emit(MemberLeaveState.Success)
                        } else {
                            throw Exception("LeaveMemberUseCase return false")
                        }
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    _leaveState.emit(MemberLeaveState.Error(e.message))
                }
            }
        }
    }

sealed class MemberLeaveState {
    data object Initial : MemberLeaveState()

    data object Loading : MemberLeaveState()

    data object Success : MemberLeaveState()

    data class Error(
        val message: String?,
    ) : MemberLeaveState()
}

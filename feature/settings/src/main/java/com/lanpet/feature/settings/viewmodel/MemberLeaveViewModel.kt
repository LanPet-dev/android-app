package com.lanpet.feature.settings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.domain.usecase.account.LeaveMemberUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MemberLeaveViewModel
    @Inject
    constructor(
        private val leaveMemberUseCase: LeaveMemberUseCase,
    ) : ViewModel() {
        private val _leaveState = MutableStateFlow<MemberLeaveState>(MemberLeaveState.Initial)
        val leaveState: StateFlow<MemberLeaveState> = _leaveState.asStateFlow()

        fun leaveMember() {
            viewModelScope.launch {
                try {
                    _leaveState.value = MemberLeaveState.Loading
                    leaveMemberUseCase().collect {
                        _leaveState.value = MemberLeaveState.Success
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    _leaveState.value = MemberLeaveState.Error(e.message)
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

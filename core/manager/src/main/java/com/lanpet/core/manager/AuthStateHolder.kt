package com.lanpet.core.manager

import com.lanpet.domain.model.AuthState
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

// TODO("Satoshi"): rename as manager
class AuthStateHolder {
    private val _authState =
        MutableStateFlow<AuthState>(
            AuthState.Initial(),
        )

    val authState = _authState.asStateFlow()

    val currentProfileDetail =
        authState
            .map { state ->
                (state as? AuthState.Success)?.profileDetail
            }.stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = null,
            )

    val userProfiles =
        authState
            .map { state ->
                (state as? AuthState.Success)?.profile ?: emptyList()
            }.stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = emptyList(),
            )

    val defaultProfile =
        authState
            .map { state ->
                (state as? AuthState.Success)?.defaultProfile ?: UserProfile(
                    id = "",
                    type = ProfileType.PET,
                    introduction = "",
                    nickname = "",
                    profileImageUri = "",
                    isDefault = false,
                )
            }.stateIn(
                scope = CoroutineScope(Dispatchers.IO),
                started = SharingStarted.WhileSubscribed(5000),
                initialValue =
                    UserProfile(
                        id = "",
                        type = ProfileType.PET,
                        introduction = "",
                        nickname = "",
                        profileImageUri = "",
                        isDefault = false,
                    ),
            )

    fun updateState(newState: AuthState) {
        _authState.value = newState
    }
}

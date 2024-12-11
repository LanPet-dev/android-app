package com.lanpet.core.manager

import com.example.model.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

//TODO("Satoshi"): rename as manager
class AuthStateHolder {
    private val _authState = MutableStateFlow<AuthState>(
        AuthState.Initial
    )

    val authState = _authState.asStateFlow()

    fun updateState(newState: AuthState) {
        _authState.value = newState
    }
}

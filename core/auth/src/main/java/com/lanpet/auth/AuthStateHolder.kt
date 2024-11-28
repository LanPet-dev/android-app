package com.lanpet.auth

import android.util.Log
import com.example.model.AuthState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthStateHolder {
    private val _authState = MutableStateFlow<AuthState>(
        AuthState.Initial
    )

    val authState = _authState.asStateFlow()

    fun updateState(newState: AuthState) {
        println(
            hashCode().toString() + " " + this::class.simpleName + " " + this::class.qualifiedName + " " + _authState.value
        )
        Log.d("AuthStateHolder", "Updating state from ${_authState.value} to $newState")
        _authState.value = newState
    }
}

package com.lanpet.profile.model

sealed class RegisterManProfileResult {
    data object Loading : RegisterManProfileResult()

    data object Success : RegisterManProfileResult()

    data class Error(
        val message: String,
    ) : RegisterManProfileResult()

    data object Initial : RegisterManProfileResult()
}

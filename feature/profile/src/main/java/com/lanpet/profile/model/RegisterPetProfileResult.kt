package com.lanpet.profile.model

sealed class RegisterPetProfileResult {
    data object Loading : RegisterPetProfileResult()

    data object Success : RegisterPetProfileResult()

    data class Error(
        val message: String,
    ) : RegisterPetProfileResult()

    data object Initial : RegisterPetProfileResult()
}

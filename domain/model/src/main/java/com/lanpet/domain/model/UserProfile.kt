package com.lanpet.domain.model

data class UserProfile(
    val id: String,
    val type: ProfileType,
    val nickname: String,
    val profileImageUri: String?,
    val introduction: String?,
    val isDefault: Boolean = false,
)

package com.lanpet.domain.model.profile

import com.lanpet.domain.model.ProfileType

data class UserProfileDetail(
    val id: String,
    val type: ProfileType,
    val nickname: String,
    val pictureUrl: String? = null,
    val introduction: String? = null,
    val representative: Boolean,
    val pet: Pet? = null,
    val butler: Butler? = null,
)

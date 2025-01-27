package com.lanpet.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val nickname: String,
    val profileImage: String?,
)

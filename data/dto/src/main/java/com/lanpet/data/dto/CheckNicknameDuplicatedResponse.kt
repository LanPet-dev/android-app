package com.lanpet.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CheckNicknameDuplicatedResponse(
    val isExist: Boolean,
)

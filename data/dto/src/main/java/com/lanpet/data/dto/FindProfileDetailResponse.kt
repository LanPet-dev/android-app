package com.lanpet.data.dto

data class FindProfileDetailResponse(
    val id: String,
    val type: String,
    val nickname: String,
    val profileImageUri: String?,
    val introduction: String?,
)

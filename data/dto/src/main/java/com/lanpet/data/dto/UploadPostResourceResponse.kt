package com.lanpet.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UploadPostResourceResponse(
    val items: List<String>,
    val totalCount: Int,
)
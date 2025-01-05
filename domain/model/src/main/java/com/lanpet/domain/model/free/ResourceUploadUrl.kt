package com.lanpet.domain.model.free

import kotlinx.serialization.Serializable

@Serializable
data class ResourceUploadUrl(
    val items: List<String>,
    val totalCount: Int,
)

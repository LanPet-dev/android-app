package com.lanpet.data.dto

import com.lanpet.domain.model.free.ResourceUploadUrl
import kotlinx.serialization.Serializable

@Serializable
data class ResourceUploadUrlResponse(
    val items: List<String>,
    val totalCount: Int,
) {
    fun toDomain() =
        ResourceUploadUrl(
            items = items,
            totalCount = totalCount,
        )
}

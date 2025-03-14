package com.lanpet.data.dto.freeboard

import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import kotlinx.serialization.Serializable

@Serializable
data class GetFreeBoardListRequestDto(
    val cursor: String?,
    val size: Int = 10,
    val category: String?,
    val direction: String,
    val profileId: String? = null,
) {
    fun toQueryMap(): Map<String, String> =
        buildMap {
            cursor?.let { put("cursor", it) }
            put("size", this@GetFreeBoardListRequestDto.size.toString())
            if (category != null) put("category", category) else put("category", "null")
            put("direction", direction)
            put("profileId", profileId ?: "null")
        }

    companion object {
        fun fromDomain(getFreeBoardPostListRequest: GetFreeBoardPostListRequest): GetFreeBoardListRequestDto =
            GetFreeBoardListRequestDto(
                cursor = getFreeBoardPostListRequest.cursor,
                size = getFreeBoardPostListRequest.size,
                category = getFreeBoardPostListRequest.freeBoardCategoryType?.value,
                direction = getFreeBoardPostListRequest.direction.name,
                profileId = getFreeBoardPostListRequest.profileId,
            )
    }
}

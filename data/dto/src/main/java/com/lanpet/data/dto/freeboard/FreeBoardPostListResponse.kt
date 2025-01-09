package com.lanpet.data.dto.freeboard

import kotlinx.serialization.Serializable

@Serializable
data class FreeBoardPostListResponse(
    val items: List<FreeBoardItemDto>?,
    val totalCount: Int?,
    val nextCursor: String?,
)

@Serializable
data class FreeBoardItemDto(
    val id: String,
    val category: String,
    val petType: String,
    val text: FreeBoardTextDto,
    val stat: FreeBoardStatDto?,
    val resources: List<FreeBoardResourceDto>?,
    val created: String,
)

@Serializable
data class FreeBoardTextDto(
    val title: String,
    val content: String,
)

@Serializable
data class FreeBoardStatDto(
    val likeCount: Int?,
    val commentCount: Int?,
)

@Serializable
data class FreeBoardResourceDto(
    val id: String,
    val url: String,
)

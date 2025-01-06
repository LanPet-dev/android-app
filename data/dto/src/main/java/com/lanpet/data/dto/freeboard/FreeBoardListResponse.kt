package com.lanpet.data.dto.freeboard

import kotlinx.serialization.Serializable

@Serializable
data class FreeBoardListResponse(
    val items: List<FreeBoardItem>?,
    val totalCount: Int?,
    val nextCursor: String?,
)

@Serializable
data class FreeBoardItem(
    val id: String,
    val category: String,
    val petType: String,
    val text: FreeBoardText,
    val stat: FreeBoardStat?,
    val resources: List<FreeBoardResource>?,
    val created: String,
)

@Serializable
data class FreeBoardText(
    val title: String,
    val content: String,
)

@Serializable
data class FreeBoardStat(
    val likeCount: Int?,
    val commentCount: Int?,
)

@Serializable
data class FreeBoardResource(
    val id: String,
    val url: String,
)

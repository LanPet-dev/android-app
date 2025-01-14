package com.lanpet.domain.model.free

import com.lanpet.domain.model.PetCategory

data class FreeBoardPost(
    val items: List<FreeBoardItem>?,
    val totalCount: Int?,
    val nextCursor: String?,
)

data class FreeBoardItem(
    val id: String,
    val category: FreeBoardCategoryType,
    val petType: PetCategory,
    val text: FreeBoardText,
    val stat: FreeBoardStat?,
    val resources: List<FreeBoardResource>?,
    val created: String,
)

data class FreeBoardText(
    val title: String,
    val content: String,
)

data class FreeBoardStat(
    val likeCount: Int?,
    val commentCount: Int?,
)

data class FreeBoardResource(
    val id: String,
    val url: String,
)


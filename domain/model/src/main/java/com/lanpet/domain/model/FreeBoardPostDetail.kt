package com.lanpet.domain.model

data class FreeBoardPostDetail(
    val id: String,
    val title: String,
    val content: String,
    val writer: String,
    val writerImage: String?,
    val petCategory: PetCategory,
    val createdAt: String,
    val likeCount: Int,
    val commentCount: Int,
    val images: List<FreeBoardResource> = emptyList(),
    val freeBoardCategory: FreeBoardCategoryType,
)

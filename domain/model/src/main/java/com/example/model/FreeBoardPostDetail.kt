package com.example.model

data class FreeBoardPostDetail(
    val id: Int,
    val title: String,
    val content: String,
    val writer: String,
    val writerImage: String?,
    val petCategory: PetCategory,
    val createdAt: String,
    val updatedAt: String,
    val likeCount: Int,
    val commentCount: Int,
    val images: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
)
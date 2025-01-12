package com.lanpet.domain.model.free

data class FreeBoardComment(
    val id: Int,
    val content: String,
    val writer: String,
    val writerImage: String?,
    val createdAt: String,
    val updatedAt: String,
    val freeBoardId: Int,
    val likeCount: Int,
    val commentCount: Int?,
    val subComments: List<FreeBoardComment> = emptyList(),
)

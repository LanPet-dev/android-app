package com.lanpet.domain.model.free

import com.lanpet.domain.model.PetCategory

data class FreeBoardPostDetail(
    val id: String,
    val title: String,
    val content: String,
    val writer: String,
    val writerImage: String?,
    val petCategory: PetCategory,
    val createdAt: String,
    val likeCount: Int,
    val isLike: Boolean,
    val commentCount: Int,
    val subCommentCount: Int,
    val images: List<FreeBoardResource> = emptyList(),
    val freeBoardCategory: FreeBoardCategoryType,
) {
    fun dislike(): FreeBoardPostDetail = this.copy(isLike = false, likeCount = likeCount - 1)

    fun like(): FreeBoardPostDetail = this.copy(isLike = true, likeCount = likeCount + 1)
}

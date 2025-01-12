package com.lanpet.data.dto.freeboard

import kotlinx.serialization.Serializable

@Serializable
data class FreeBoardCommentResponse(
    val items: List<CommentItem>,
    val totalCount: Int,
    val nextCursor: String,
)

@Serializable
data class CommentItem(
    val id: String,
    val profile: Profile,
    val comment: String,
)

@Serializable
data class Profile(
    val nickName: String,
)

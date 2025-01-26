package com.lanpet.data.dto.freeboard

import com.lanpet.domain.model.PaginationData
import com.lanpet.domain.model.PaginationInfo
import com.lanpet.domain.model.free.FreeBoardComment
import kotlinx.serialization.Serializable

@Serializable
data class FreeBoardCommentResponse(
    val items: List<CommentItemDto>?,
    val totalCount: Int? = 0,
    val nextCursor: String?,
) {
    fun toDomain(): PaginationData<List<FreeBoardComment>> =
        PaginationData(
            paginationInfo =
            PaginationInfo(
                hasNext = nextCursor != null,
                nextCursor = nextCursor,
            ),
            data = items?.map { it.toDomain() } ?: emptyList(),
        )
}

@Serializable
data class CommentItemDto(
    val id: String,
    val profile: ProfileDto,
    val comment: String,
    val created: String,
) {
    fun toDomain(): FreeBoardComment =
        FreeBoardComment(
            id = id,
            profile = profile.toDomain(),
            comment = comment,
            createdAt = created,
        )
}

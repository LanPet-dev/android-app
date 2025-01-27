package com.lanpet.data.dto.freeboard

import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.domain.model.free.FreeBoardPostDetail
import kotlinx.serialization.Serializable

@Serializable
data class FreeBoardDetailItemDto(
    val id: String,
    val category: String,
    val petType: String,
    val text: FreeBoardTextDto,
    val stat: FreeBoardStatDto?,
    val resources: List<FreeBoardResourceDto>?,
    val created: String,
    val profile: ProfileDto,
    val profileId: String,
    val isLike: Boolean,
)

fun FreeBoardDetailItemDto.toDomain(): FreeBoardPostDetail =
    FreeBoardPostDetail(
        id = id,
        title = text.title,
        content = text.content,
        writer = profile.nickname,
        writerImage = null,
        petCategory = PetCategory.valueOf(petType),
        createdAt = created,
        likeCount = stat?.likeCount ?: 0,
        commentCount = stat?.commentCount ?: 0,
        images = resources?.map { it.toDomain() } ?: emptyList(),
        freeBoardCategory = FreeBoardCategoryType.valueOf(category),
        isLike = isLike,
        subCommentCount = stat?.subCommentCount ?: 0,
    )

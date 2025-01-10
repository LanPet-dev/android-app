package com.lanpet.data.dto.freeboard

import com.lanpet.domain.model.FreeBoardCategoryType
import com.lanpet.domain.model.FreeBoardPostDetail
import com.lanpet.domain.model.PetCategory
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
    val profileId: String,
)

fun FreeBoardDetailItemDto.toDomain(): FreeBoardPostDetail =
    FreeBoardPostDetail(
        id = id,
        title = text.title,
        content = text.content,
        writer = id,
        writerImage = null,
        petCategory = PetCategory.valueOf(petType),
        createdAt = created,
        likeCount = stat?.likeCount ?: 0,
        commentCount = stat?.commentCount ?: 0,
        images = resources?.map { it.toDomain() } ?: emptyList(),
        freeBoardCategory = FreeBoardCategoryType.valueOf(category),
    )

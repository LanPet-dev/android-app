package com.lanpet.data.dto

import com.lanpet.data.dto.freeboard.ProfileDto
import kotlinx.serialization.Serializable

@Serializable
data class SubCommentDetailDto(
    val id: String,
    val profile: ProfileDto,
    val comment: String,
    val created: String,
)

fun SubCommentDetailDto.toDomain(): com.lanpet.domain.model.free.FreeBoardSubComment =
    com.lanpet.domain.model.free.FreeBoardSubComment(
        id = id,
        profile = profile.toDomain(),
        comment = comment,
        createdAt = created,
    )

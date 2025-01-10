package com.lanpet.data.dto.freeboard

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

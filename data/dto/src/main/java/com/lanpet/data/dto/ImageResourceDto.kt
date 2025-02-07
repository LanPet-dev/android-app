package com.lanpet.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ImageResourceDto(
    val id: String,
    val url: String,
)

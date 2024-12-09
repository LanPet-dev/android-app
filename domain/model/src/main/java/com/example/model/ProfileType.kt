package com.example.model

import kotlinx.serialization.Serializable

@Serializable
enum class ProfileType(val value: String) {
    PET(value = "PET"), BUTLER(value = "BUTLER");

    companion object {
        fun fromValue(value: String): ProfileType? = entries.find { it.value == value }
    }
}



package com.example.model

import kotlinx.serialization.Serializable

@Serializable
enum class AuthorityType {
    ADMIN, USER;

    companion object {
        fun fromValue(value: String): AuthorityType? = entries.find { it.name == value }
    }
}
package com.lanpet.data.dto.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "default_user_profile",
)
data class DefaultUserProfileEntity(
    @PrimaryKey
    val accountId: String,
    val profileId: String,
)

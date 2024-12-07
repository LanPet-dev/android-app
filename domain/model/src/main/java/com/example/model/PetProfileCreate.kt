package com.example.model

import android.net.Uri
import androidx.annotation.Keep

@Keep
data class PetProfileCreate(
    val profileImageUri: Uri?,
    val nickName: String,
    val petCategory: PetCategory?,
    val species: String,
    val bio: String,
)

package com.example.model

import android.net.Uri
import androidx.annotation.Keep

@Keep
data class ManProfileCreate(
    val profileImageUri: Uri?,
    val nickName: String,
    val preferPets: List<PetCategory> = emptyList<PetCategory>(),
    val age: Age?,
    val bio: String,
)

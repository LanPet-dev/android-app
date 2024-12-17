package com.lanpet.domain.model

import android.net.Uri
import com.lanpet.domain.model.profile.Butler

data class ManProfileCreate(
    val profileImageUri: Uri?,
    val nickName: String,
    val preferPets: List<PetCategory> = emptyList<PetCategory>(),
    val bio: String,
    val type: ProfileType,
    val butler: Butler,
)

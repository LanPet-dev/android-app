package com.lanpet.domain.model

import android.net.Uri
import com.lanpet.domain.model.profile.Pet

data class PetProfileCreate(
    val type: ProfileType = ProfileType.PET,
    val profileImageUri: Uri? = null,
    val nickName: String,
    val bio: String? = null,
    val pet: Pet,
)

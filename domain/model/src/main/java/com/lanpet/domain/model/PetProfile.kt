package com.lanpet.domain.model

import android.net.Uri
import com.lanpet.domain.model.profile.Pet

data class PetProfile(
    val type: ProfileType?,
    val profileImageUri: Uri?,
    val nickName: String?,
    val bio: String?,
    val pet: Pet?,
)

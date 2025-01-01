package com.lanpet.myprofile.model

import android.net.Uri
import androidx.compose.runtime.Stable
import com.lanpet.domain.model.ProfileType
import com.lanpet.domain.model.profile.Pet

@Stable
data class PetProfileUpdate(
    val id: String,
    val type: ProfileType?,
    val profileImageUri: Uri?,
    val nickName: String?,
    val bio: String?,
    val pet: Pet?,
)

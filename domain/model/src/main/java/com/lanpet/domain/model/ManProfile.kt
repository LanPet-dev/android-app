package com.lanpet.domain.model

import android.net.Uri
import com.lanpet.domain.model.profile.Butler

data class ManProfile(
    val type: ProfileType?,
    val profileImageUri: Uri?,
    val nickName: String?,
    val bio: String?,
    val butler: Butler?,
)

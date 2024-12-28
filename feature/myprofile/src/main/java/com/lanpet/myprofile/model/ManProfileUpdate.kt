package com.lanpet.myprofile.model

import android.net.Uri
import androidx.compose.runtime.Stable
import com.lanpet.domain.model.profile.Butler

@Stable
data class ManProfileUpdate(
    val profileImageUri: Uri?,
    val nickName: String?,
    val bio: String?,
    val butler: Butler?,
)

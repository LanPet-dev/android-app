package com.lanpet.domain.model.free

import android.net.Uri
import com.lanpet.domain.model.PetCategory

data class FreeBoardPostCreate(
    val profileId: String?,
    val boardCategory: FreeBoardCategoryType?,
    val petCategory: PetCategory?,
    val title: String,
    val body: String,
    val imageList: List<Uri>?,
)

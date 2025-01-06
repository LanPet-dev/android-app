package com.lanpet.domain.model

import android.net.Uri

data class FreeBoardPostCreate(
    val profileId: String?,
    val boardCategory: FreeBoardCategoryType?,
    val petCategory: PetCategory?,
    val title: String,
    val body: String,
    val imageList: List<Uri>?,
)

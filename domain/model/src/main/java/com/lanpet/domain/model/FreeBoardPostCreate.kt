package com.lanpet.domain.model

import android.net.Uri

data class FreeBoardPostCreate(
    val boardCategory: String,
    val petCategory: String,
    val title: String,
    val body: String,
    val imageList: List<Uri>?
)

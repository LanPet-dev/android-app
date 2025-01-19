package com.lanpet.domain.repository

import kotlinx.coroutines.flow.Flow

interface S3UploadRepository {
    fun uploadImageResource(
        url: String,
        byteArray: ByteArray,
    ): Flow<Unit>
}

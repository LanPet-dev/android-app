package com.lanpet.data.repository

import com.lanpet.data.service.S3UploadApiService
import com.lanpet.domain.repository.S3UploadRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class S3UploadRepositoryImpl
    @Inject
    constructor(
        private val s3UploadApiService: S3UploadApiService,
    ) : S3UploadRepository {
        override fun uploadImageResource(
            url: String,
            byteArray: ByteArray,
        ): Flow<Unit> =
            flow {
                val requestBody = byteArray.toRequestBody("image/jpeg".toMediaTypeOrNull())
                val res = s3UploadApiService.uploadImage(url = url, body = requestBody)
                emit(res)
            }.flowOn(Dispatchers.IO)
    }

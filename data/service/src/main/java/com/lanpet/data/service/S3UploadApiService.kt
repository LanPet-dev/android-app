package com.lanpet.data.service

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Url

interface S3UploadApiService {
    @PUT
    suspend fun uploadImage(
        @Url url: String,
        @Header("Content-Type") contentType: String = "image/jpeg",
        @Body body: RequestBody,
    )
}

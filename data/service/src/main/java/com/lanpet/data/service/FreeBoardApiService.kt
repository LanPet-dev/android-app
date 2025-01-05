package com.lanpet.data.service

import com.lanpet.data.dto.CreateFreeBoardPostRequest
import com.lanpet.data.dto.CreateFreeBoardPostResponse
import com.lanpet.data.dto.ResourceUploadUrlResponse
import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.FreeBoardPostDetail
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface FreeBoardApiService {
    @GET
    suspend fun getFreeBoardPostList(): List<FreeBoardPost>

    @GET
    suspend fun getFreeBoardPostDetail(id: String): FreeBoardPostDetail

    @GET
    suspend fun getFreeBoardPostCommentList(id: String): List<FreeBoardComment>

    @POST("/sarangbangs")
    suspend fun createFreeBoardPost(
        @Body
        createFreeBoardPostRequest: CreateFreeBoardPostRequest,
    ): CreateFreeBoardPostResponse

    @POST("/sarangbangs/{sarangbangId}/resources")
    suspend fun getResourceUploadUrl(
        @Path("sarangbangId")
        sarangbangId: String,
        @Query("size")
        size: Int,
    ): ResourceUploadUrlResponse
}

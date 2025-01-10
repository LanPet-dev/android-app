package com.lanpet.data.service

import com.lanpet.data.dto.CreateFreeBoardPostRequest
import com.lanpet.data.dto.CreateFreeBoardPostResponse
import com.lanpet.data.dto.DoPostLikeRequest
import com.lanpet.data.dto.ResourceUploadUrlResponse
import com.lanpet.data.dto.freeboard.FreeBoardDetailItemDto
import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.FreeBoardPostDetail
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FreeBoardApiService {
    @GET(PATH)
    suspend fun getFreeBoardPostList(
        @QueryMap queries: Map<String, String>,
    ): FreeBoardPost

    @GET("$PATH/{id}")
    suspend fun getFreeBoardPostDetail(
        @Path("id") id: String,
    ): FreeBoardDetailItemDto

    @GET(PATH)
    suspend fun getFreeBoardPostCommentList(id: String): List<FreeBoardComment>

    @POST(PATH)
    suspend fun createFreeBoardPost(
        @Body
        createFreeBoardPostRequest: CreateFreeBoardPostRequest,
    ): CreateFreeBoardPostResponse

    @POST("$PATH/{sarangbangId}/resources")
    suspend fun getResourceUploadUrl(
        @Path("sarangbangId")
        sarangbangId: String,
        @Query("size")
        size: Int,
    ): ResourceUploadUrlResponse

    @POST("$PATH/{sarangbangId}/likes")
    suspend fun doPostLike(
        @Path("sarangbangId")
        sarangbangId: String,
        @Body
        doPostLikeRequest: DoPostLikeRequest,
    ): Unit

    @DELETE("$PATH/{sarangbangId}/likes")
    suspend fun cancelPostLike(
        @Path("sarangbangId")
        sarangbangId: String,
        @Query("profileId")
        profileId: String,
    ): Unit

    companion object {
        const val PATH = "/sarangbangs"
    }
}

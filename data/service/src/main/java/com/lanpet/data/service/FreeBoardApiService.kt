package com.lanpet.data.service

import com.lanpet.data.dto.CreateFreeBoardPostRequest
import com.lanpet.data.dto.CreateFreeBoardPostResponse
import com.lanpet.data.dto.DoPostLikeRequest
import com.lanpet.data.dto.ResourceUploadUrlResponse
import com.lanpet.data.dto.SubCommentDetailDto
import com.lanpet.data.dto.freeboard.FreeBoardCommentResponse
import com.lanpet.data.dto.freeboard.FreeBoardDetailItemDto
import com.lanpet.data.dto.freeboard.FreeBoardWriteCommentRequest
import com.lanpet.data.dto.freeboard.FreeBoardWriteSubCommentResponse
import com.lanpet.domain.model.free.FreeBoardPost
import com.lanpet.domain.model.free.FreeBoardWriteComment
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
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
        @Query("reader") profileId: String,
    ): FreeBoardDetailItemDto

    /**
     * 사랑방 답글을 삭제합니다.
     *
     * @param sarangbangId
     * @param commentId
     * @return
     */
    @DELETE("$PATH/{sarangbangId}/comments/{commentId}")
    suspend fun deleteFreeBoardComment(
        @Path("sarangbangId") sarangbangId: String,
        @Path("commentId") commentId: String,
    ): Boolean

    /**
     * 사랑방 답글을 수정합니다.
     *
     * @param sarangbangId
     * @param commentId
     * @param content
     * @return
     */
    @PATCH("$PATH/{sarangbangId}/comments/{commentId}")
    suspend fun updateFreeBoardComment(
        @Path("sarangbangId") sarangbangId: String,
        @Path("commentId") commentId: String,
        @Body
        content: String,
    ): Boolean

    @GET("$PATH/{id}/comments")
    suspend fun getFreeBoardPostCommentList(
        @Path("id")
        id: String,
        @QueryMap queries: Map<String, String>?,
    ): FreeBoardCommentResponse

    @GET("$PATH/{postId}/comments/{commentId}/sub-comments")
    suspend fun getFreeBoardSubCommentList(
        @Path("postId")
        postId: String,
        @Path("commentId")
        commentId: String,
        @QueryMap queries: Map<String, String>?,
    ): FreeBoardCommentResponse

    @POST("$PATH/{postId}/comments/{commentId}/sub-comments")
    suspend fun writeSubComment(
        @Path("postId")
        postId: String,
        @Path("commentId")
        commentId: String,
        @Body
        freeBoardWriteCommentRequest: FreeBoardWriteCommentRequest,
    ): FreeBoardWriteSubCommentResponse

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

    /**
     * 사랑방 모든 리소스 삭제
     *
     * @param [sarangbangId]
     * @return [Boolean]
     */
    @DELETE("$PATH/{sarangbangId}/resources")
    suspend fun deleteResource(
        @Path("sarangbangId")
        sarangbangId: String,
    ): Boolean

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

    @POST("$PATH/{sarangbangId}/comments")
    suspend fun writeComment(
        @Path("sarangbangId")
        sarangbangId: String,
        @Body
        writeComment: FreeBoardWriteComment,
    ): Response<ResponseBody>

    @GET("$PATH/{sarangbangId}/comments/{commentId}/sub-comments/{subCommentId}")
    suspend fun getSubCommentDetail(
        @Path("sarangbangId")
        sarangbangId: String,
        @Path("commentId")
        commentId: String,
        @Path("subCommentId")
        subCommentId: String,
    ): SubCommentDetailDto

    companion object {
        const val PATH = "/sarangbangs"
    }
}

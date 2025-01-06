package com.lanpet.data.service

import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.FreeBoardPostDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface FreeBoardApiService {
    @GET
    suspend fun getFreeBoardPostList(): List<FreeBoardPost>

    @GET
    suspend fun getFreeBoardPostDetail(
        @Path("id") id: String,
    ): FreeBoardPostDetail

    @GET
    suspend fun getFreeBoardPostCommentList(id: String): List<FreeBoardComment>
}

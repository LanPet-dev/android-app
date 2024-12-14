package com.lanpet.data.service

import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.FreeBoardPostDetail
import retrofit2.http.GET

interface FreeBoardApiService {
    @GET
    suspend fun getFreeBoardPostList(): List<FreeBoardPost>

    @GET
    suspend fun getFreeBoardPostDetail(id: String): FreeBoardPostDetail

    @GET
    suspend fun getFreeBoardPostCommentList(id: String): List<FreeBoardComment>
}

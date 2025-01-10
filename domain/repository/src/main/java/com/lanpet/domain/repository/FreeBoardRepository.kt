package com.lanpet.domain.repository

import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.FreeBoardPostCreate
import com.lanpet.domain.model.FreeBoardPostDetail
import com.lanpet.domain.model.FreeBoardPostLike
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.model.free.ResourceUploadUrl
import kotlinx.coroutines.flow.Flow

interface FreeBoardRepository {
    fun getFreeBoardPostList(getFreeBoardPostListRequest: GetFreeBoardPostListRequest): Flow<FreeBoardPost>

    fun getFreeBoardDetail(id: String): Flow<FreeBoardPostDetail>

    fun getFreeBoardCommentList(id: String): Flow<List<FreeBoardComment>>

    fun createFreeBoardPost(freeBoardPostCreate: FreeBoardPostCreate): Flow<String>

    fun getResourceUploadUrl(
        sarangbangId: String,
        size: Int,
    ): Flow<ResourceUploadUrl>

    fun doPostLike(
        sarangbangId: String,
        freeBoardPostLike: FreeBoardPostLike,
    ): Flow<Boolean>

    fun cancelPostLike(
        sarangbangId: String,
        profileId: String,
    ): Flow<Boolean>
}

package com.lanpet.domain.repository

import com.lanpet.domain.model.PaginationData
import com.lanpet.domain.model.free.FreeBoardComment
import com.lanpet.domain.model.free.FreeBoardPost
import com.lanpet.domain.model.free.FreeBoardPostCreate
import com.lanpet.domain.model.free.FreeBoardPostDetail
import com.lanpet.domain.model.free.FreeBoardPostLike
import com.lanpet.domain.model.free.FreeBoardSubComment
import com.lanpet.domain.model.free.FreeBoardWriteComment
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.model.free.ResourceUploadUrl
import com.lanpet.domain.model.pagination.CursorDirection
import kotlinx.coroutines.flow.Flow

interface FreeBoardRepository {
    fun getFreeBoardPostList(getFreeBoardPostListRequest: GetFreeBoardPostListRequest): Flow<FreeBoardPost>

    fun getFreeBoardDetail(
        id: String,
        profileId: String,
    ): Flow<FreeBoardPostDetail>

    fun getFreeBoardCommentList(
        id: String,
        cursor: String?,
        size: Int?,
        direction: CursorDirection?,
    ): Flow<PaginationData<List<FreeBoardComment>>>

    fun getFreeBoardSubCommentList(
        postId: String,
        commentId: String,
        size: Int?,
        cursor: String?,
        direction: CursorDirection?,
    ): Flow<PaginationData<List<FreeBoardSubComment>>>

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

    fun writeComment(
        sarangbangId: String,
        writeComment: FreeBoardWriteComment,
    ): Flow<Boolean>

    fun writeSubComment(
        postId: String,
        commentId: String,
        writeComment: FreeBoardWriteComment,
    ): Flow<Boolean>
}

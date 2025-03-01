package com.lanpet.data.repository

import com.lanpet.data.dto.CreateFreeBoardPostRequest
import com.lanpet.data.dto.DoPostLikeRequest
import com.lanpet.data.dto.freeboard.FreeBoardWriteCommentRequest
import com.lanpet.data.dto.freeboard.GetFreeBoardListRequestDto
import com.lanpet.data.dto.freeboard.toDomain
import com.lanpet.data.dto.toDomain
import com.lanpet.data.service.FreeBoardApiService
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
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

class FreeBoardRepositoryImpl
    @Inject
    constructor(
        private val freeBoardApiService: FreeBoardApiService,
    ) : FreeBoardRepository {
        // TODO: Remove this dummy data
        override fun getFreeBoardPostList(getFreeBoardPostListRequest: GetFreeBoardPostListRequest): Flow<FreeBoardPost> =
            flow {
                val requestDto = GetFreeBoardListRequestDto.fromDomain(getFreeBoardPostListRequest)
                val res =
                    freeBoardApiService.getFreeBoardPostList(
                        requestDto.toQueryMap(),
                    )
                emit(res)
            }.flowOn(Dispatchers.IO)

        override fun getFreeBoardDetail(
            id: String,
            profileId: String,
        ): Flow<FreeBoardPostDetail> =
            flow {
                emit(freeBoardApiService.getFreeBoardPostDetail(id, profileId).toDomain())
            }.flowOn(Dispatchers.IO)

        override fun getFreeBoardCommentList(
            id: String,
            cursor: String?,
            size: Int?,
            direction: CursorDirection?,
        ): Flow<PaginationData<List<FreeBoardComment>>> =
            flow {
                try {
                    val queries = mutableMapOf<String, String>()
                    cursor?.let { queries["cursor"] = it }
                    size?.let { queries["size"] = it.toString() }
                    direction?.let { queries["direction"] = it.name }

                    emit(freeBoardApiService.getFreeBoardPostCommentList(id, queries).toDomain())
                } catch (e: Exception) {
                    Timber.e(e)
                    throw e
                }
            }.flowOn(Dispatchers.IO)

        override fun getFreeBoardSubCommentList(
            postId: String,
            commentId: String,
            size: Int?,
            cursor: String?,
            direction: CursorDirection?,
        ): Flow<PaginationData<List<FreeBoardSubComment>>> =
            flow {
                try {
                    val queries = mutableMapOf<String, String>()
                    cursor?.let { queries["cursor"] = it }
                    size?.let { queries["size"] = it.toString() }
                    direction?.let { queries["direction"] = it.name }

                    emit(
                        freeBoardApiService
                            .getFreeBoardSubCommentList(postId, commentId, queries)
                            .toSubCommentDomain(),
                    )
                } catch (e: Exception) {
                    Timber.e(e)
                    throw e
                }
            }

        override fun getFreeBoardSubCommentDetail(
            postId: String,
            commentId: String,
            subCommentId: String,
        ): Flow<FreeBoardSubComment> =
            flow<FreeBoardSubComment> {
                val subCommentDetail =
                    freeBoardApiService.getSubCommentDetail(postId, commentId, subCommentId)
                emit(subCommentDetail.toDomain())
            }.flowOn(Dispatchers.IO)

        override fun createFreeBoardPost(freeBoardPostCreate: FreeBoardPostCreate): Flow<String> =
            flow {
                val request = CreateFreeBoardPostRequest.fromDomainToCreateRequest(freeBoardPostCreate)
                val res = freeBoardApiService.createFreeBoardPost(request)
                emit(res.id)
            }.flowOn(Dispatchers.IO)

        override fun getResourceUploadUrl(
            sarangbangId: String,
            size: Int,
        ): Flow<ResourceUploadUrl> =
            flow {
                val res = freeBoardApiService.getResourceUploadUrl(sarangbangId, size)
                emit(res.toDomain())
            }.flowOn(Dispatchers.IO)

        override fun doPostLike(
            sarangbangId: String,
            freeBoardPostLike: FreeBoardPostLike,
        ): Flow<Boolean> =
            flow {
                try {
                    val request = DoPostLikeRequest.fromDomainToRequest(freeBoardPostLike)
                    freeBoardApiService.doPostLike(sarangbangId, request)
                    emit(true)
                } catch (e: Exception) {
                    Timber.e(e)
                    throw e
                }
            }.flowOn(Dispatchers.IO)

        override fun cancelPostLike(
            sarangbangId: String,
            profileId: String,
        ): Flow<Boolean> =
            flow {
                try {
                    freeBoardApiService.cancelPostLike(sarangbangId, profileId)
                    emit(true)
                } catch (e: Exception) {
                    Timber.e(e)
                    throw e
                }
            }.flowOn(Dispatchers.IO)

        override fun writeComment(
            sarangbangId: String,
            writeComment: FreeBoardWriteComment,
        ): Flow<String> =
            flow {
                try {
                    val res = freeBoardApiService.writeComment(sarangbangId, writeComment)
                    res.body()?.string()?.let { responseBody ->
                        val jsonObject = JSONObject(responseBody)
                        emit(jsonObject.getString("id"))
                    } ?: throw Exception("Post comment response is invalid")
                } catch (e: Exception) {
                    Timber.e(e)
                    throw e
                }
            }

        override fun writeSubComment(
            postId: String,
            commentId: String,
            writeComment: FreeBoardWriteComment,
        ): Flow<String> =
            flow {
                try {
                    val subCommentId =
                        freeBoardApiService.writeSubComment(
                            postId,
                            commentId,
                            FreeBoardWriteCommentRequest.fromDomain(freeBoardWriteComment = writeComment),
                        )
                    emit(subCommentId.id)
                } catch (e: Exception) {
                    Timber.e(e)
                    throw e
                }
            }
    }

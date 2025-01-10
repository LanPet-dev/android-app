package com.lanpet.data.repository

import com.lanpet.data.dto.CreateFreeBoardPostRequest
import com.lanpet.data.dto.DoPostLikeRequest
import com.lanpet.data.dto.freeboard.GetFreeBoardListRequestDto
import com.lanpet.data.dto.freeboard.toDomain
import com.lanpet.data.service.FreeBoardApiService
import com.lanpet.domain.model.FreeBoardCategoryType
import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardItem
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.FreeBoardPostCreate
import com.lanpet.domain.model.FreeBoardPostDetail
import com.lanpet.domain.model.FreeBoardPostLike
import com.lanpet.domain.model.FreeBoardResource
import com.lanpet.domain.model.FreeBoardStat
import com.lanpet.domain.model.FreeBoardText
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.model.free.ResourceUploadUrl
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class FreeBoardRepositoryImpl
    @Inject
    constructor(
        private val freeBoardApiService: FreeBoardApiService,
    ) : FreeBoardRepository {
        override fun getFreeBoardPostList(getFreeBoardPostListRequest: GetFreeBoardPostListRequest): Flow<FreeBoardPost> =
            flow {
                val requestDto = GetFreeBoardListRequestDto.fromDomain(getFreeBoardPostListRequest)
//                val res =
//                    freeBoardApiService.getFreeBoardPostList(
//                        requestDto.toQueryMap(),
//                    )
                val res =
                    FreeBoardPost(
                        items =
                            listOf(
                                FreeBoardItem(
                                    id = "1",
                                    category = FreeBoardCategoryType.COMMUNICATION,
                                    petType = PetCategory.DOG,
                                    text =
                                        FreeBoardText(
                                            title = "제목1",
                                            content = "내용1",
                                        ),
                                    stat =
                                        FreeBoardStat(
                                            likeCount = 10,
                                            commentCount = 5,
                                        ),
                                    resources =
                                        listOf(
                                            FreeBoardResource(
                                                id = "1",
                                                url = "https://www.naver.com",
                                            ),
                                        ),
                                    created = "2021-08-01T12:34:56+09:00",
                                ),
                                FreeBoardItem(
                                    id = "2",
                                    category = FreeBoardCategoryType.COMMUNICATION,
                                    petType = PetCategory.CAT,
                                    text =
                                        FreeBoardText(
                                            title = "제목2",
                                            content = "내용2",
                                        ),
                                    stat =
                                        FreeBoardStat(
                                            likeCount = 10,
                                            commentCount = 5,
                                        ),
                                    resources =
                                        listOf(
                                            FreeBoardResource(
                                                id = "2",
                                                url = "https://www.naver.com",
                                            ),
                                        ),
                                    created = "2021-08-01T12:34:56+09:00",
                                ),
                                FreeBoardItem(
                                    id = "3",
                                    category = FreeBoardCategoryType.CURIOUS,
                                    petType = PetCategory.FISH,
                                    text =
                                        FreeBoardText(
                                            title = "제목3",
                                            content = "내용3",
                                        ),
                                    stat =
                                        FreeBoardStat(
                                            likeCount = 10,
                                            commentCount = 5,
                                        ),
                                    resources =
                                        listOf(
                                            FreeBoardResource(
                                                id = "3",
                                                url = "https://www.naver.com",
                                            ),
                                        ),
                                    created = "2021-08-01T12:34:56+09:00",
                                ),
                                FreeBoardItem(
                                    id = "4",
                                    category = FreeBoardCategoryType.RECOMMENDATION,
                                    petType = PetCategory.TURTLE,
                                    text =
                                        FreeBoardText(
                                            title = "제목4",
                                            content = "내용4",
                                        ),
                                    stat =
                                        FreeBoardStat(
                                            likeCount = 10,
                                            commentCount = 5,
                                        ),
                                    resources =
                                        listOf(
                                            FreeBoardResource(
                                                id = "4",
                                                url = "https://www.naver.com",
                                            ),
                                        ),
                                    created = "2021-08-01T12:34:56+09:00",
                                ),
                                FreeBoardItem(
                                    id = "5",
                                    category = FreeBoardCategoryType.RECOMMENDATION,
                                    petType = PetCategory.OTHER,
                                    text =
                                        FreeBoardText(
                                            title = "제목5",
                                            content = "내용5",
                                        ),
                                    stat =
                                        FreeBoardStat(
                                            likeCount = 10,
                                            commentCount = 5,
                                        ),
                                    resources =
                                        listOf(
                                            FreeBoardResource(
                                                id = "5",
                                                url = "https://www.naver.com",
                                            ),
                                        ),
                                    created = "2021-08-01T12:34:56+09:00",
                                ),
                                FreeBoardItem(
                                    id = "6",
                                    category = FreeBoardCategoryType.CURIOUS,
                                    petType = PetCategory.OTHER,
                                    text =
                                        FreeBoardText(
                                            title = "제목6",
                                            content = "내용6",
                                        ),
                                    stat =
                                        FreeBoardStat(
                                            likeCount = 10,
                                            commentCount = 5,
                                        ),
                                    resources =
                                        listOf(
                                            FreeBoardResource(
                                                id = "6",
                                                url = "https://www.naver.com",
                                            ),
                                        ),
                                    created = "2021-08-01T12:34:56+09:00",
                                ),
                                FreeBoardItem(
                                    id = "7",
                                    category = FreeBoardCategoryType.RECOMMENDATION,
                                    petType = PetCategory.OTHER,
                                    text =
                                        FreeBoardText(
                                            title = "제목7",
                                            content = "내용7",
                                        ),
                                    stat =
                                        FreeBoardStat(
                                            likeCount = 10,
                                            commentCount = 5,
                                        ),
                                    resources =
                                        listOf(
                                            FreeBoardResource(
                                                id = "7",
                                                url = "https://www.naver.com",
                                            ),
                                        ),
                                    created = "2021-08-01T12:34:56+09:00",
                                ),
                                FreeBoardItem(
                                    id = "8",
                                    category = FreeBoardCategoryType.RECOMMENDATION,
                                    petType = PetCategory.OTHER,
                                    text =
                                        FreeBoardText(
                                            title = "제목8",
                                            content = "내용8",
                                        ),
                                    stat =
                                        FreeBoardStat(
                                            likeCount = 10,
                                            commentCount = 5,
                                        ),
                                    resources =
                                        listOf(
                                            FreeBoardResource(
                                                id = "8",
                                                url = "https://www.naver.com",
                                            ),
                                        ),
                                    created = "2021-08-01T12:34:56+09:00",
                                ),
                            ),
                        totalCount = 3,
                        nextCursor = "nextCursor",
                    )
                emit(res)
            }.flowOn(Dispatchers.IO)

        // TODO: Remove this dummy data
        override fun getFreeBoardDetail(id: String): Flow<FreeBoardPostDetail> =
            flow {
                emit(freeBoardApiService.getFreeBoardPostDetail(id).toDomain())
            }.flowOn(Dispatchers.IO)

        // TODO: Remove this dummy data
        override fun getFreeBoardCommentList(id: String): Flow<List<FreeBoardComment>> =
            flow {
                emit(
                    listOf(
                        FreeBoardComment(
                            id = 1,
                            content = "댓글1",
                            writer = "작성자1",
                            writerImage = "https://dummyimage.com/600x400/000/fff",
                            createdAt = "2021-08-01T12:34:56+09:00",
                            updatedAt = "2021-08-01T12:34:56+09:00",
                            freeBoardId = 1,
                            likeCount = 10,
                            commentCount = 5,
                            subComments =
                                listOf(
                                    FreeBoardComment(
                                        id = 1,
                                        content = "대댓글1",
                                        writer = "작성자1",
                                        writerImage = "https://dummyimage.com/600x400/000/fff",
                                        createdAt = "2021-08-01T12:34:56+09:00",
                                        updatedAt = "2021-08-01T12:34:56+09:00",
                                        freeBoardId = 1,
                                        likeCount = 10,
                                        commentCount = 5,
                                    ),
                                    FreeBoardComment(
                                        id = 2,
                                        content = "대댓글2",
                                        writer = "작성자2",
                                        writerImage = "https://dummyimage.com/600x400/000/fff",
                                        createdAt = "2021-08-01T12:34:56+09:00",
                                        updatedAt = "2021-08-01T12:34:56+09:00",
                                        freeBoardId = 1,
                                        likeCount = 10,
                                        commentCount = 5,
                                    ),
                                ),
                        ),
                        FreeBoardComment(
                            id = 2,
                            content = "댓글2",
                            writer = "작성자2",
                            writerImage = "https://dummyimage.com/600x400/000/fff",
                            createdAt = "2021-08-01T12:34:56+09:00",
                            updatedAt = "2021-08-01T12:34:56+09:00",
                            freeBoardId = 1,
                            likeCount = 10,
                            commentCount = 5,
                        ),
                    ),
                )
//            emit(freeBoardApiService.getFreeBoardPostCommentList(id))
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
                    emit(false)
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
                    emit(false)
                }
            }.flowOn(Dispatchers.IO)
    }

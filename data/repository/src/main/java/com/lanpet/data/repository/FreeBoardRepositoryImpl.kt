package com.lanpet.data.repository

import com.lanpet.data.dto.CreateFreeBoardPostRequest
import com.lanpet.data.dto.freeboard.GetFreeBoardListRequestDto
import com.lanpet.data.service.FreeBoardApiService
import com.lanpet.domain.model.FreeBoardComment
import com.lanpet.domain.model.FreeBoardPost
import com.lanpet.domain.model.FreeBoardPostCreate
import com.lanpet.domain.model.FreeBoardPostDetail
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.model.free.ResourceUploadUrl
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FreeBoardRepositoryImpl
    @Inject
    constructor(
        private val freeBoardApiService: FreeBoardApiService,
    ) : FreeBoardRepository {
        override fun getFreeBoardPostList(getFreeBoardPostListRequest: GetFreeBoardPostListRequest): Flow<FreeBoardPost> =
            flow {
                val requestDto = GetFreeBoardListRequestDto.fromDomain(getFreeBoardPostListRequest)
                emit(
                    freeBoardApiService.getFreeBoardPostList(
                        requestDto.toQueryMap(),
                    ),
                )
            }.flowOn(Dispatchers.IO)

        // TODO: Remove this dummy data
        override fun getFreeBoardDetail(id: String): Flow<FreeBoardPostDetail> =
            flow {
                emit(
                    FreeBoardPostDetail(
                        id = 1,
                        title = "제목",
                        content =
                            "awheoifhioawehf oihwieo fiowa hfiowaehf oiawioef hio\nawhiofhwaioefhio\n" +
                                "awehfiowaheiofhioawehfiohioawefhio\n",
                        writer = "작성자",
                        writerImage = "https://dummyimage.com/600x400/000/fff",
                        createdAt = "2021-08-01T12:34:56+09:00",
                        updatedAt = "2021-08-01T12:34:56+09:00",
                        likeCount = 10,
                        commentCount = 5,
                        images =
                            listOf(
                                "https://example.com/image1.jpg",
                                "https://example.com/image2.jpg",
                            ),
                        tags = listOf("태그1", "태그2"),
                        petCategory = PetCategory.DOG,
                    ),
                )
//            emit(freeBoardApiService.getFreeBoardPostDetail(id))
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
    }

package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.free.FreeBoardPost
import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetFreeBoardPostListUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        suspend operator fun invoke(
            imageBaseUrl: String = "",
            getFreeBoardPostListRequest: GetFreeBoardPostListRequest,
        ): FreeBoardPost {
            val res = freeBoardRepository.getFreeBoardPostList(getFreeBoardPostListRequest).first()

            return res.copy(
                items =
                    res.items?.map {
                        it.copy(
                            resources =
                                it.resources?.map {
                                    it.copy(
                                        url = imageBaseUrl + it.url,
                                    )
                                },
                        )
                    },
            )
        }
    }

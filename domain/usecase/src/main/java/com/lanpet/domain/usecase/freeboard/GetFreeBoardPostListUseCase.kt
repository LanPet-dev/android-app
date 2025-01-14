package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.model.free.GetFreeBoardPostListRequest
import com.lanpet.domain.repository.FreeBoardRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetFreeBoardPostListUseCase
    @Inject
    constructor(
        private val freeBoardRepository: FreeBoardRepository,
    ) {
        operator fun invoke(
            imageBaseUrl: String = "",
            getFreeBoardPostListRequest: GetFreeBoardPostListRequest,
        ) = freeBoardRepository.getFreeBoardPostList(getFreeBoardPostListRequest).map {
            it.copy(
                items =
                    it.items?.map {
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

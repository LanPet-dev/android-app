package com.lanpet.domain.usecase.freeboard

import com.lanpet.domain.repository.FreeBoardRepository
import javax.inject.Inject

class GetFreeBoardCommentListUseCase @Inject constructor(
    private val freeBoardRepository: FreeBoardRepository
) {
    operator fun invoke(postId: Int) = freeBoardRepository.getFreeBoardCommentList(postId.toString())
}
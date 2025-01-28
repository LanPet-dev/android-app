package com.lanpet.data.dto.freeboard

import com.lanpet.domain.model.free.FreeBoardWriteComment

data class FreeBoardWriteCommentRequest(
    val profileId: String,
    val comment: String,
) {
    companion object {
        fun fromDomain(freeBoardWriteComment: FreeBoardWriteComment) =
            FreeBoardWriteCommentRequest(
                profileId = freeBoardWriteComment.profileId,
                comment = freeBoardWriteComment.comment,
            )
    }
}

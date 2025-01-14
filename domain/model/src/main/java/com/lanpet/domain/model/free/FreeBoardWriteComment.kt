package com.lanpet.domain.model.free

data class FreeBoardWriteComment(
    val profileId: String,
    val comment: String,
) {
    init {
        require(profileId.isNotBlank()) { "profileId validation fail" }
        require(comment.length in 1..100) { "comment validation fail" }
    }
}

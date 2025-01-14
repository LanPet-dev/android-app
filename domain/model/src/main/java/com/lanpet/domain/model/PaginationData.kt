package com.lanpet.domain.model

data class PaginationData<T>(
    val paginationInfo: PaginationInfo,
    val data: T,
)

data class PaginationInfo(
    val hasNext: Boolean,
    val nextCursor: String?,
)

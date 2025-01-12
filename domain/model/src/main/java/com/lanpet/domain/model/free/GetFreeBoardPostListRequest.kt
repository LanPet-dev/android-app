package com.lanpet.domain.model.free

import com.lanpet.domain.model.pagination.CursorDirection

data class GetFreeBoardPostListRequest(
    val cursor: String?,
    // Same as limit. Default limit is 10.
    val size: Int = 10,
    // When FreeBoardCategoryType is null, it means that all categories are included.
    val freeBoardCategoryType: FreeBoardCategoryType?,
    val direction: CursorDirection = CursorDirection.NEXT,
)

package com.lanpet.domain.model

enum class FreeBoardCategoryType(
    val value: String?,
    val title: String,
) {
    ALL(title = "전체", value = null),
    COMMUNICATE(title = "소통해요", value = "COMMUNICATION"),
    RECOMMEND(title = "추천해요", value = "RECOMMENDATION"),
    QUESTION(title = "궁금해요", value = "CURIOUS"),
}

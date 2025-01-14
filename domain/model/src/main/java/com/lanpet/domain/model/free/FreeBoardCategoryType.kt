package com.lanpet.domain.model.free

enum class FreeBoardCategoryType(
    val value: String?,
    val title: String,
) {
    ALL(title = "전체", value = null),
    COMMUNICATION(title = "소통해요", value = "COMMUNICATION"),
    RECOMMENDATION(title = "추천해요", value = "RECOMMENDATION"),
    CURIOUS(title = "궁금해요", value = "CURIOUS"),
    ;

    companion object {
        fun fromValue(value: String?): FreeBoardCategoryType? = FreeBoardCategoryType.entries.find { it.value == value }
    }
}

package com.example.model

import androidx.annotation.Keep
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@Keep
data class FreeBoardComment(
    val id: Int,
    val content: String,
    val writer: String,
    val writerImage: String?,
    val createdAt: String,
    val updatedAt: String,
    val freeBoardId: Int,
    val likeCount: Int,
    val commentCount: Int?,
    val subComments: List<FreeBoardComment> = emptyList()
)

@Keep
fun FreeBoardComment.createdAtPostString(
    currentTime: Date = Date()
): String {
    try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")


        val date = inputFormat.parse(createdAt) ?: return ""

        assert(currentTime >= date)

        val timeDiff = (currentTime.time - date.time) / 1000 // 초 단위 차이

        return when {
            timeDiff < 60 -> "방금 전"
            timeDiff < 60 * 60 -> "${timeDiff / 60}분 전"
            timeDiff < 60 * 60 * 24 -> "${timeDiff / (60 * 60)}시간 전"
            timeDiff < 60 * 60 * 24 * 30 -> "${timeDiff / (60 * 60 * 24)}일 전"
            timeDiff < 60 * 60 * 24 * 365 -> "${timeDiff / (60 * 60 * 24 * 30)}달 전"
            else -> "${timeDiff / (60 * 60 * 24 * 365)}년 전"
        }
    } catch (e: Exception) {
        return ""
    }
}

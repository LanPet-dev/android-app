package com.lanpet.core.common

import androidx.compose.foundation.layout.WindowInsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun WindowInsets.Companion.zero() = WindowInsets(
    0, 0, 0, 0
)


fun loremIpsum() = """
    Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
    Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
    Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
    Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
""".trimIndent()

fun createdAtPostString(
    createdAt: String,
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

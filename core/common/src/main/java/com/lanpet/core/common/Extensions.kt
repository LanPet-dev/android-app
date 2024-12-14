package com.lanpet.core.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.WindowInsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun WindowInsets.Companion.zero() =
    WindowInsets(
        0,
        0,
        0,
        0,
    )

fun loremIpsum() =
    """
    Lorem ipsum dolor sit amet, consectetur adipiscing elit. 
    Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. 
    Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. 
    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
    Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
    """.trimIndent()

@SuppressLint("SimpleDateFormat")
fun createdAtPostString(
    createdAt: String,
    currentTime: String? = null,
): String {
    try {
        val createdAtTimeMillis = createdAt.toLocalDate().time
        val currentTimeTimeMillis =
            currentTime?.let { SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(it)?.time ?: 999999 }
                ?: Date().time

        val timeDiff = (currentTimeTimeMillis - createdAtTimeMillis) / 1000 // 초 단위 차이

        return when {
            timeDiff < 60 -> "방금 전"
            timeDiff < 60 * 60 -> "${timeDiff / 60}분 전"
            timeDiff < 60 * 60 * 24 -> "${timeDiff / (60 * 60)}시간 전"
            timeDiff < 60 * 60 * 24 * 30 -> "${timeDiff / (60 * 60 * 24)}일 전"
            timeDiff < 60 * 60 * 24 * 365 -> "${timeDiff / (60 * 60 * 24 * 30)}달 전"
            else -> "${timeDiff / (60 * 60 * 24 * 365)}년 전"
        }
    } catch (e: Exception) {
        e.printStackTrace()
        return ""
    }
}

fun String.toLocalDate(
    dateFormat: String = "yyyy-MM-dd'T'HH:mm:ssXXX",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC"),
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    return parser.parse(this) ?: throw IllegalArgumentException("Invalid date format")
}

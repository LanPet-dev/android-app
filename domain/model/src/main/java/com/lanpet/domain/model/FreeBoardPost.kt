package com.lanpet.domain.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

data class FreeBoardPost(
    val id: Int,
    val petCategory: PetCategory,
    val title: String,
    val tags: List<String>,
    val content: String,
    val images: List<String>,
    val createdAt: String,
    val updatedAt: String,
    val likeCount: Int,
    val commentCount: Int,
) {
    // UTC 문자열을 파싱하여 한국 시간으로 변환
    val createdAtKorString: String
        get() {
            try {
                val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
                inputFormat.timeZone = TimeZone.getTimeZone("UTC")

                val outputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                outputFormat.timeZone = TimeZone.getTimeZone("Asia/Seoul")

                val date = inputFormat.parse(createdAt) ?: return ""
                return outputFormat.format(date)
            } catch (e: Exception) {
                return ""
            }
        }
}


package com.lanpet.core.common

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.layout.WindowInsets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.removeWhiteSpace() = this.replace("\\s".toRegex(), "")

fun String.removeLineBreak() = this.replace("\n", "")

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
    createdAt: String?,
    currentTime: String? = null,
): String {
    if (createdAt == null) return ""

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
    dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC"),
): Date {
    val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
    parser.timeZone = timeZone
    parser.isLenient = false
    return parser.parse(this) ?: throw IllegalArgumentException("Invalid date format")
}

fun Date.toUtcDateString(
    dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX",
    timeZone: TimeZone = TimeZone.getTimeZone("UTC"),
): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    formatter.timeZone = timeZone
    return formatter.format(this)
}

fun Date.toYyyyMmDdHhMmSs(dateFormat: String = "yyyy-MM-dd HH:mm:ss"): String {
    val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
    return formatter.format(this)
}

fun Context.createProfileImageUri(): Uri {
    // TODO: Refactor filename
    val fileName = "lanpet_${System.currentTimeMillis()}.jpg"

    return contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        },
    )!!
}

fun Context.toast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT,
) {
    Toast.makeText(this, message, duration).show()
}

fun List<Uri>.toByteArrayList(context: Context): List<ByteArray> =
    this.mapNotNull { uri ->
        uri.toCompressedByteArray(context)
    }

fun Uri.toCompressedByteArray(
    context: Context,
    targetWidth: Int = 800,
    targetHeight: Int = 800,
    compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 80,
): ByteArray? =
    try {
        context.contentResolver.openInputStream(this)?.use { inputStream ->
            val originalBitmap = BitmapFactory.decodeStream(inputStream)

            val originalWidth = originalBitmap.width
            val originalHeight = originalBitmap.height

            val aspectRatio = originalWidth.toFloat() / originalHeight
            val newWidth: Int
            val newHeight: Int
            if (aspectRatio > 1) {
                newWidth = targetWidth
                newHeight = (targetWidth / aspectRatio).toInt()
            } else {
                newHeight = targetHeight
                newWidth = (targetHeight * aspectRatio).toInt()
            }

            val resizedBitmap =
                Bitmap.createScaledBitmap(
                    originalBitmap,
                    newWidth,
                    newHeight,
                    true,
                )

            ByteArrayOutputStream().use { outputStream ->
                resizedBitmap.compress(compressFormat, quality, outputStream)
                outputStream.toByteArray()
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }

fun <T> Flow<T>.safeScopedCall(
    block: suspend (T) -> Unit,
    scope: CoroutineScope,
    onFailure: suspend (Throwable) -> Unit = {},
    onSuccess: suspend () -> Unit = {},
    onComplete: suspend () -> Unit = {},
) {
    scope.launch {
        runCatching {
            this@safeScopedCall
                .onCompletion {
                    onComplete()
                }.collect {
                    block(it)
                }
        }.onFailure {
            onFailure(it)
        }.onSuccess {
            onSuccess()
        }
    }
}

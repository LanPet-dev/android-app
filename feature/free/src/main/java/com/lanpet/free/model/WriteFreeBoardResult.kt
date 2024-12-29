package com.lanpet.free.model

sealed class WriteFreeBoardResult {
    data object Loading : WriteFreeBoardResult()

    data object Success : WriteFreeBoardResult()

    data class Error(
        val message: String,
    ) : WriteFreeBoardResult()

    data object Initial : WriteFreeBoardResult()
}
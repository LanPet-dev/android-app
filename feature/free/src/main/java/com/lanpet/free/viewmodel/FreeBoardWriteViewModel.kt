package com.lanpet.free.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.FormValidator
import com.lanpet.free.model.WriteFreeBoardResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FreeBoardWriteViewModel
    @Inject
    constructor(
    ): ViewModel() {
        private val _freeBoardPostCreate =
            MutableStateFlow(
                FreeBoardPostCreate(
                    boardCategory = "",
                    petCategory = "",
                    title = "",
                    body = "",
                    imageList = emptyList(),
                ),
            )
        val freeBoardPostCreate : StateFlow<FreeBoardPostCreate> = _freeBoardPostCreate.asStateFlow()

        private val _writeFreeBoardResult =
            MutableSharedFlow<WriteFreeBoardResult>()
        val writeFreeBoardResult : SharedFlow<WriteFreeBoardResult> =
            _writeFreeBoardResult.asSharedFlow()

        private val freeBoardWriteValidator =
            FreeBoardWriteValidator(
                boardCategory =
                FormValidator { category ->
                    if (category.isEmpty()) {
                        FormValidationStatus.Invalid("게시글 카테고리를 선택해주세요.")
                    } else {
                        FormValidationStatus.Valid()
                    }
                },
                petCategory =
                FormValidator { category ->
                    if (category.isEmpty()) {
                        FormValidationStatus.Invalid("반려동물 카테고리를 선택해주세요.")
                    } else {
                        FormValidationStatus.Valid()
                    }
                },
                title =
                FormValidator { title ->
                    if (title.isEmpty()) {
                        FormValidationStatus.Invalid("제목을 입력해주세요.")
                    } else {
                        FormValidationStatus.Valid()
                    }
                },
                body =
                FormValidator { body ->
                    if (body.isEmpty()) {
                        FormValidationStatus.Invalid("내용을 입력해주세요.")
                    } else {
                        FormValidationStatus.Valid()
                    }
                },
                imageList =
                FormValidator { imageList ->
                    FormValidationStatus.Valid()
                }
            )
        private val _freeBoardWriteValidationResult =
            MutableStateFlow(
                FreeBoardWriteValidationResult(
                    boardCategory = FormValidationStatus.Initial(),
                    petCategory = FormValidationStatus.Initial(),
                    title = FormValidationStatus.Initial(),
                    body = FormValidationStatus.Initial(),
                    imageList = FormValidationStatus.Initial(),
                ),
            )
        val freeBoardWriteValidationResult: StateFlow<FreeBoardWriteValidationResult> =
            _freeBoardWriteValidationResult.asStateFlow()

        fun setBoardCategory(category: String) {
            _freeBoardWriteValidationResult.value =
                _freeBoardWriteValidationResult.value.copy(
                    boardCategory = freeBoardWriteValidator.boardCategory.validate(category),
                )
            _freeBoardPostCreate.value = _freeBoardPostCreate.value.copy(boardCategory = category)
        }

        fun setPetCategory(category: String) {
            _freeBoardWriteValidationResult.value =
                _freeBoardWriteValidationResult.value.copy(
                    petCategory = freeBoardWriteValidator.petCategory.validate(category),
                )
            _freeBoardPostCreate.value = _freeBoardPostCreate.value.copy(petCategory = category)
        }

        fun setTitle(title: String) {
            _freeBoardWriteValidationResult.value =
                _freeBoardWriteValidationResult.value.copy(
                    title = freeBoardWriteValidator.title.validate(title),
                )
            _freeBoardPostCreate.value = _freeBoardPostCreate.value.copy(title = title)
        }

        fun setBody(body: String) {
            _freeBoardWriteValidationResult.value =
                _freeBoardWriteValidationResult.value.copy(
                    body = freeBoardWriteValidator.body.validate(body),
                )
            _freeBoardPostCreate.value = _freeBoardPostCreate.value.copy(body = body)
        }

        fun addImage(uri: Uri) {
            val currentImages = _freeBoardPostCreate.value.imageList?.toMutableList() ?: mutableListOf()
            currentImages.add(uri)

            _freeBoardWriteValidationResult.value = _freeBoardWriteValidationResult.value.copy(
                imageList = freeBoardWriteValidator.imageList.validate(currentImages)
            )
            _freeBoardPostCreate.value = _freeBoardPostCreate.value.copy(imageList = currentImages)
        }

        fun removeImage(uri: Uri) {
            val currentImages = _freeBoardPostCreate.value.imageList?.toMutableList() ?: mutableListOf()
            currentImages.remove(uri)

            _freeBoardWriteValidationResult.value = _freeBoardWriteValidationResult.value.copy(
                imageList = freeBoardWriteValidator.imageList.validate(currentImages)
            )
            _freeBoardPostCreate.value = _freeBoardPostCreate.value.copy(imageList = currentImages)
        }

        fun setImages(imageList: List<Uri>) {
            _freeBoardWriteValidationResult.value =
                _freeBoardWriteValidationResult.value.copy(
                    imageList = freeBoardWriteValidator.imageList.validate(imageList),
                )
            _freeBoardPostCreate.value = _freeBoardPostCreate.value.copy(imageList = imageList)
        }
    }


data class FreeBoardWriteValidator(
    val boardCategory: FormValidator<String>,
    val petCategory: FormValidator<String>,
    val title: FormValidator<String>,
    val body: FormValidator<String>,
    val imageList: FormValidator<List<Uri>>
)

data class FreeBoardWriteValidationResult(
    val boardCategory: FormValidationStatus,
    val petCategory: FormValidationStatus,
    val title: FormValidationStatus,
    val body: FormValidationStatus,
    val imageList: FormValidationStatus,
)

data class FreeBoardPostCreate(
    val boardCategory: String,
    val petCategory: String,
    val title: String,
    val body: String,
    val imageList: List<Uri>?
)

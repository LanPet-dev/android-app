package com.lanpet.free.viewmodel

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lanpet.core.common.FormValidationStatus
import com.lanpet.core.common.FormValidator
import com.lanpet.domain.model.FreeBoardCategoryType
import com.lanpet.domain.model.FreeBoardPostCreate
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.usecase.freeboard.CreateFreeBoardPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FreeBoardWriteViewModel
    @Inject
    constructor(
        savedStateHandle: SavedStateHandle,
        private val postFreeBoardUseCase: CreateFreeBoardPostUseCase,
    ) : ViewModel() {

        private val _uiState =
            MutableStateFlow(
                CreateFreeBoardPostUiState(
                    freeBoardPostCreate =
                        FreeBoardPostCreate(
                            profileId = null,
                            boardCategory = null,
                            petCategory = null,
                            title = "",
                            body = "",
                            imageList = emptyList(),
                        ),
                )
            )
        val uiState = _uiState.asStateFlow()

        val isValidState =
            _uiState
                .map { state ->
                    val boardCategoryValidation =
                        freeBoardWriteValidator.boardCategory.validate(
                            state.freeBoardPostCreate?.boardCategory,
                        )
                    val petCategoryValidation =
                        freeBoardWriteValidator.petCategory.validate(state.freeBoardPostCreate?.petCategory)
                    val titleValidation =
                        freeBoardWriteValidator.title.validate(state.freeBoardPostCreate?.title)
                    val bodyValidation =
                        freeBoardWriteValidator.body.validate(state.freeBoardPostCreate?.body)
                    val imageListValidation =
                        freeBoardWriteValidator.imageList.validate(state.freeBoardPostCreate?.imageList)

                    boardCategoryValidation is FormValidationStatus.Valid &&
                            petCategoryValidation is FormValidationStatus.Valid &&
                            titleValidation is FormValidationStatus.Valid &&
                            bodyValidation is FormValidationStatus.Valid &&
                            imageListValidation is FormValidationStatus.Valid

                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000),
                    initialValue = false,
                )

        private val freeBoardWriteValidator =
            FreeBoardWriteValidator(
                profileId =
                FormValidator { id ->
                    if (id.isNullOrBlank()) {
                        FormValidationStatus.Invalid("프로필 정보를 불러올 수 없습니다.")
                    } else {
                        FormValidationStatus.Valid()
                    }
                },
                boardCategory =
                    FormValidator { category ->
                        if (category?.value.isNullOrEmpty()) {
                            FormValidationStatus.Invalid("게시글 카테고리를 선택해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                petCategory =
                    FormValidator { category ->
                        if (category?.value.isNullOrEmpty()) {
                            FormValidationStatus.Invalid("반려동물 카테고리를 선택해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                title =
                    FormValidator { title ->
                        if (title.isNullOrEmpty()) {
                            FormValidationStatus.Invalid("제목을 입력해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                body =
                    FormValidator { body ->
                        if (body.isNullOrEmpty()) {
                            FormValidationStatus.Invalid("내용을 입력해주세요.")
                        } else {
                            FormValidationStatus.Valid()
                        }
                    },
                imageList =
                    FormValidator { imageList ->
                        FormValidationStatus.Valid()
                    },
            )

        private val _uiEvent = MutableSharedFlow<Boolean>()
        val uiEvent = _uiEvent.asSharedFlow()


        /*private val _freeBoardWriteValidationResult =
            MutableStateFlow(
                FreeBoardWriteValidationResult(
                    boardCategory = FormValidationStatus.Initial(),
                    petCategory = FormValidationStatus.Initial(),
                    title = FormValidationStatus.Initial(),
                    body = FormValidationStatus.Initial(),
                    imageList = FormValidationStatus.Valid(),
                ),
            )
        val freeBoardWriteValidationResult: StateFlow<FreeBoardWriteValidationResult> =
            _freeBoardWriteValidationResult.asStateFlow()


        private val _writeFreeBoardResult =
            MutableSharedFlow<WriteFreeBoardResult>()
        val writeFreeBoardResult: SharedFlow<WriteFreeBoardResult> =
            _writeFreeBoardResult.asSharedFlow()*/

        fun setProfileId(id: String) {
            _uiState.value =
                _uiState.value.copy(
                    freeBoardPostCreate =
                    _uiState.value.freeBoardPostCreate?.copy(
                        profileId = id,
                    ),
                    validationStatus =
                    _uiState.value.validationStatus.copy(
                        profileId = freeBoardWriteValidator.profileId.validate(id),
                    ),
                )
        }

        fun setBoardCategory(category: FreeBoardCategoryType) {
            _uiState.value =
                _uiState.value.copy(
                    freeBoardPostCreate =
                        _uiState.value.freeBoardPostCreate?.copy(
                            boardCategory = category,
                        ),
                    validationStatus =
                        _uiState.value.validationStatus.copy(
                            boardCategory = freeBoardWriteValidator.boardCategory.validate(category),
                        ),
                )
        }

        fun setPetCategory(category: PetCategory) {
            _uiState.value =
                _uiState.value.copy(
                    freeBoardPostCreate =
                    _uiState.value.freeBoardPostCreate?.copy(
                        petCategory = category,
                    ),
                    validationStatus =
                    _uiState.value.validationStatus.copy(
                        petCategory = freeBoardWriteValidator.petCategory.validate(category),
                    ),
                )
        }

        fun setTitle(title: String) {
            _uiState.value =
                _uiState.value.copy(
                    freeBoardPostCreate =
                    _uiState.value.freeBoardPostCreate?.copy(
                        title = title,
                    ),
                    validationStatus =
                    _uiState.value.validationStatus.copy(
                        title = freeBoardWriteValidator.title.validate(title),
                    ),
                )
        }

        fun setBody(body: String) {
            _uiState.value =
                _uiState.value.copy(
                    freeBoardPostCreate =
                    _uiState.value.freeBoardPostCreate?.copy(
                        body = body,
                    ),
                    validationStatus =
                    _uiState.value.validationStatus.copy(
                        body = freeBoardWriteValidator.body.validate(body),
                    ),
                )
        }

        fun addImage(uri: Uri) {
            val currentImages = _uiState.value.freeBoardPostCreate?.imageList?.toMutableList() ?: mutableListOf()
            if (currentImages.contains(uri)) {
                return
            } else {
                currentImages.add(uri)
            }
            _uiState.value =
                _uiState.value.copy(
                    freeBoardPostCreate =
                    _uiState.value.freeBoardPostCreate?.copy(
                        imageList = currentImages,
                    ),
                    validationStatus =
                    _uiState.value.validationStatus.copy(
                        imageList = freeBoardWriteValidator.imageList.validate(currentImages),
                    ),
                )
        }

        fun removeImage(uri: Uri) {
            val currentImages = _uiState.value.freeBoardPostCreate?.imageList?.toMutableList() ?: mutableListOf()
            currentImages.remove(uri)

            _uiState.value =
                _uiState.value.copy(
                    freeBoardPostCreate =
                    _uiState.value.freeBoardPostCreate?.copy(
                        imageList = currentImages,
                    ),
                    validationStatus =
                    _uiState.value.validationStatus.copy(
                        imageList = freeBoardWriteValidator.imageList.validate(currentImages),
                    ),
                )
        }

        fun writeFreeBoardPost() {
            val freeBoardPostCreate = _uiState.value.freeBoardPostCreate ?: return

            if (!isValidState.value) {
                return
            }

            viewModelScope.launch {
                runCatching {
                    postFreeBoardUseCase(
                        freeBoardPostCreate = freeBoardPostCreate
                    ).collect {
                        _uiEvent.emit(true)
                    }
                }.onFailure {
                    _uiEvent.emit(false)
                }
            }
        }
    }

@Stable
data class CreateFreeBoardPostUiState(
    val freeBoardPostCreate: FreeBoardPostCreate?,
    val nicknameDuplicateCheck: Boolean? = null,
    val validationStatus: FreeBoardWriteValidationStatus =
        FreeBoardWriteValidationStatus(
            profileId = FormValidationStatus.Initial(),
            boardCategory = FormValidationStatus.Initial(),
            petCategory = FormValidationStatus.Initial(),
            title = FormValidationStatus.Initial(),
            body = FormValidationStatus.Initial(),
            imageList = FormValidationStatus.Initial()
        ),
)

@Stable
data class FreeBoardWriteValidator(
    val profileId: FormValidator<String?>,
    val boardCategory: FormValidator<FreeBoardCategoryType?>,
    val petCategory: FormValidator<PetCategory?>,
    val title: FormValidator<String?>,
    val body: FormValidator<String?>,
    val imageList: FormValidator<List<Uri>?>,
)

@Stable
data class FreeBoardWriteValidationStatus(
    val profileId: FormValidationStatus,
    val boardCategory: FormValidationStatus,
    val petCategory: FormValidationStatus,
    val title: FormValidationStatus,
    val body: FormValidationStatus,
    val imageList: FormValidationStatus,
) {
    val isValid: Boolean
        get() =
            profileId is FormValidationStatus. Valid &&
                    boardCategory is FormValidationStatus.Valid &&
                    petCategory is FormValidationStatus.Valid &&
                    title is FormValidationStatus.Valid &&
                    body is FormValidationStatus.Valid &&
                    imageList is FormValidationStatus.Valid
}

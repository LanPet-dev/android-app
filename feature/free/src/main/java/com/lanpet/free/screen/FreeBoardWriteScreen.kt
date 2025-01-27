package com.lanpet.free.screen

import android.Manifest
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavOptions
import coil.compose.AsyncImage
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.createProfileImageUri
import com.lanpet.core.common.myiconpack.Close
import com.lanpet.core.common.rememberCameraPermissionLauncher
import com.lanpet.core.common.rememberCameraTakePictureLauncher
import com.lanpet.core.common.rememberGalleryLauncher
import com.lanpet.core.common.toast
import com.lanpet.core.common.widget.ActionButton
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.CommonCenterAlignedAppBarTitle
import com.lanpet.core.common.widget.CommonIconButtonBox
import com.lanpet.core.common.widget.CommonSubHeading1
import com.lanpet.core.common.widget.IOSActionSheet
import com.lanpet.core.common.widget.LanPetCenterAlignedTopAppBar
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.WhiteColor
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.PetCategory
import com.lanpet.domain.model.free.FreeBoardCategoryType
import com.lanpet.free.R
import com.lanpet.free.navigation.FreeBoardWrite
import com.lanpet.free.viewmodel.FreeBoardWriteUiEvent
import com.lanpet.free.viewmodel.FreeBoardWriteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardWriteScreen(
    modifier: Modifier = Modifier,
    freeBoardWriteViewModel: FreeBoardWriteViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit = {},
    onNavigateToFreeBoardDetail: (String, String, String, NavOptions) -> Unit = { _, _, _, _ -> },
) {
    val verticalScrollState = rememberScrollState()

    val profileId =
        LocalAuthManager.current.defaultUserProfile
            .collectAsStateWithLifecycle()
            .value.id

    val nickname =
        LocalAuthManager.current.defaultUserProfile
            .collectAsStateWithLifecycle()
            .value.nickname

    val freeBoardPostCreate by freeBoardWriteViewModel.uiState.collectAsStateWithLifecycle()
    val loadingState by freeBoardWriteViewModel.loadingState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        freeBoardWriteViewModel.setProfileId(profileId)

        freeBoardWriteViewModel.uiEvent.collect { event ->
            when (event) {
                is FreeBoardWriteUiEvent.Success -> {
                    val navOptions =
                        NavOptions
                            .Builder()
                            .setPopUpTo(FreeBoardWrite, inclusive = true)
                            .build()

                    onNavigateToFreeBoardDetail(event.postId, profileId, nickname, navOptions)
                }

                else -> {
                    context.toast(context.getString(R.string.toast_post_create_fail))
                }
            }
        }
    }

    Scaffold(
        topBar = {
            LanPetCenterAlignedTopAppBar(
                navigationIcon = {
                    CommonIconButtonBox(
                        content = {
                            Image(
                                imageVector = MyIconPack.Close,
                                contentDescription = "ic_close",
                                colorFilter = ColorFilter.tint(MaterialTheme.customColorScheme.defaultIconColor),
                            )
                        },
                        onClick =
                        onNavigateUp,
                    )
                },
                title = {
                    CommonCenterAlignedAppBarTitle(
                        title = stringResource(R.string.title_freeboard_write),
                    )
                },
            )
        },
    ) {
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it),
        ) {
            Column(
                modifier =
                    Modifier
                        .verticalScroll(
                            verticalScrollState,
                        ),
            ) {
                if (loadingState) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .weight(1f),
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    SelectBoardSection(
                        selectedCategory = freeBoardPostCreate.freeBoardPostCreate?.boardCategory,
                    ) { category ->
                        freeBoardWriteViewModel.setBoardCategory(category)
                    }
                    LineWithSpacer()
                    SelectPetSection(
                        selectedCategory = freeBoardPostCreate.freeBoardPostCreate?.petCategory,
                    ) { category ->
                        freeBoardWriteViewModel.setPetCategory(category)
                    }
                    LineWithSpacer()
                    TitleInputSection { title ->
                        freeBoardWriteViewModel.setTitle(title)
                    }
                    ContentInputSection { body ->
                        freeBoardWriteViewModel.setBody(body)
                    }
                    ImagePickSection(
                        isEnable =
                            (
                                freeBoardPostCreate.freeBoardPostCreate?.imageList?.size
                                    ?: 0
                            ) <= 5,
                    ) { uri ->
                        freeBoardWriteViewModel.addImage(uri)
                    }
                    if ((freeBoardPostCreate.freeBoardPostCreate?.imageList?.size ?: 0) > 0) {
                        Text(
                            stringResource(R.string.attach_photo_desc),
                            style =
                                MaterialTheme.customTypography().body3RegularSingle.copy(
                                    color = GrayColor.Gray400,
                                ),
                            modifier = Modifier.padding(start = LanPetDimensions.Margin.small),
                        )
                        Spacer(modifier = Modifier.padding(bottom = LanPetDimensions.Margin.small))
                        LazyRow(
                            modifier = Modifier.padding(start = LanPetDimensions.Margin.small),
                        ) {
                            val imageList: List<Uri>? =
                                freeBoardPostCreate.freeBoardPostCreate?.imageList
                            if (!imageList.isNullOrEmpty()) {
                                items(imageList.size) { index ->
                                    ImageWithDeleteIcon(
                                        uri = imageList[index],
                                    ) {
                                        freeBoardWriteViewModel.removeImage(imageList[index])
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                    CommonButton(
                        title = stringResource(R.string.complete_action_freeboard_write),
                        isActive = freeBoardWriteViewModel.isValidState.collectAsStateWithLifecycle().value,
                        modifier = Modifier.padding(horizontal = LanPetDimensions.Margin.small),
                    ) {
                        freeBoardWriteViewModel.writeFreeBoardPost(context)
                    }
                    Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                }
            }
        }
    }
}

@Composable
private fun LineWithSpacer() {
    Spacer(
        modifier =
            Modifier
                .padding(vertical = LanPetDimensions.Spacing.small)
                .fillMaxWidth()
                .size(LanPetDimensions.Spacing.xxxxSmall)
                .background(
                    color = MaterialTheme.customColorScheme.spacerLine,
                ),
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectBoardSection(
    selectedCategory: FreeBoardCategoryType?,
    modifier: Modifier = Modifier,
    onCategorySelect: (FreeBoardCategoryType) -> Unit = {},
) {
    val categories =
        listOf(
            FreeBoardCategoryType.COMMUNICATION,
            FreeBoardCategoryType.RECOMMENDATION,
            FreeBoardCategoryType.CURIOUS,
        )

    Column {
        CommonSubHeading1(
            title = stringResource(R.string.board_category_hint_freeboard_write),
            modifier =
                Modifier.padding(
                    horizontal = LanPetDimensions.Margin.medium,
                    vertical = LanPetDimensions.Margin.small,
                ),
        )
        FlowRow(
            modifier = Modifier.padding(horizontal = LanPetDimensions.Margin.xSmall),
        ) {
            categories.forEach { category ->
                SelectableChip.Rounded(
                    title = category.title,
                    isSelected = selectedCategory == category,
                ) { onCategorySelect(category) }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectPetSection(
    selectedCategory: PetCategory?,
    modifier: Modifier = Modifier,
    onCategorySelect: (PetCategory) -> Unit = {},
) {
    Column {
        CommonSubHeading1(
            title = stringResource(R.string.pet_category_hint_freeboard_write),
            modifier =
                Modifier.padding(
                    horizontal = LanPetDimensions.Margin.medium,
                    vertical = LanPetDimensions.Margin.small,
                ),
        )
        FlowRow(
            modifier = Modifier.padding(horizontal = LanPetDimensions.Margin.xSmall),
        ) {
            PetCategory.entries.forEach { category ->
                SelectableChip.Rounded(
                    title = category.value,
                    isSelected = selectedCategory == category,
                ) { onCategorySelect(category) }
            }
        }
    }
}

@Composable
private fun TitleInputSection(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
) {
    var input by rememberSaveable {
        mutableStateOf("")
    }

    val maxLength = 20

    Column(
        modifier =
            Modifier
                .padding(horizontal = LanPetDimensions.Margin.medium)
                .padding(bottom = LanPetDimensions.Margin.medium),
    ) {
        CommonSubHeading1(
            title = stringResource(R.string.title_title_input_freeboard_write),
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xSmall))
        Box {
            OutlinedTextField(
                modifier =
                    Modifier
                        .fillMaxWidth(),
                value = input,
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
                maxLines = 1,
                minLines = 1,
                colors =
                    OutlinedTextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = GrayColor.LIGHT,
                        focusedIndicatorColor = GrayColor.LIGHT,
                        disabledIndicatorColor = GrayColor.LIGHT,
                        focusedPlaceholderColor = GrayColor.MEDIUM,
                        unfocusedPlaceholderColor = GrayColor.MEDIUM,
                        disabledPlaceholderColor = GrayColor.MEDIUM,
                        cursorColor = GrayColor.MEDIUM,
                        focusedContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                        unfocusedContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                        disabledContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                        errorContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                    ),
                singleLine = false,
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {
                        input = newText
                        onTextChange(newText)
                    }
                },
                placeholder = {
                    Text(
                        stringResource(R.string.title_title_input_freeboard_write),
                        style = MaterialTheme.typography.bodyMedium.copy(color = GrayColor.LIGHT),
                    )
                },
            )

            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .padding(end = LanPetDimensions.Margin.medium),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Text(
                    text = "${input.length}/${maxLength}자",
                    style = MaterialTheme.typography.bodySmall.copy(color = GrayColor.LIGHT),
                )
            }
        }
    }
}

@Composable
private fun ContentInputSection(
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
) {
    var input by rememberSaveable {
        mutableStateOf("")
    }

    val maxLength = 500

    Column(
        modifier =
            Modifier.padding(
                horizontal = LanPetDimensions.Margin.medium,
            ),
    ) {
        CommonSubHeading1(
            title = stringResource(R.string.title_content_input_freeboard_write),
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xSmall))
        Box {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = input,
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
                maxLines = 10,
                minLines = 10,
                colors =
                    OutlinedTextFieldDefaults.colors().copy(
                        unfocusedIndicatorColor = GrayColor.LIGHT,
                        focusedIndicatorColor = GrayColor.LIGHT,
                        disabledIndicatorColor = GrayColor.LIGHT,
                        focusedPlaceholderColor = GrayColor.MEDIUM,
                        unfocusedPlaceholderColor = GrayColor.MEDIUM,
                        disabledPlaceholderColor = GrayColor.MEDIUM,
                        cursorColor = GrayColor.MEDIUM,
                        focusedContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                        unfocusedContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                        disabledContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                        errorContainerColor = MaterialTheme.customColorScheme.textFieldBackground,
                    ),
                singleLine = false,
                onValueChange = { newText ->
                    if (newText.length <= maxLength) {
                        input = newText
                        onTextChange(newText)
                    }
                },
                placeholder = {
                    Text(
                        stringResource(R.string.title_content_input_freeboard_write),
                        style = MaterialTheme.typography.bodyMedium.copy(color = GrayColor.LIGHT),
                    )
                },
            )

            Box(
                modifier =
                    Modifier
                        .matchParentSize()
                        .padding(bottom = 16.dp, end = 16.dp),
                contentAlignment = Alignment.BottomEnd,
            ) {
                Text(
                    text = "${input.length}/${maxLength}자",
                    style = MaterialTheme.typography.bodySmall.copy(color = GrayColor.LIGHT),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImagePickSection(
    modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    onImageSelect: (Uri) -> Unit = { },
) {
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var imageUri: Uri? by rememberSaveable {
        mutableStateOf(null)
    }

    // 갤러리 launcher
    val galleryLauncher =
        rememberGalleryLauncher(
            onGetUri = { uri ->
                uri?.let {
                    imageUri = it
                    onImageSelect(it)
                }
            },
        )

    // 카메라 launcher
    val cameraLauncher =
        rememberCameraTakePictureLauncher { success ->
            if (success) {
                imageUri?.let { uri ->
                    onImageSelect(uri)
                }
            }
        }

    // 카메라 권한 launcher
    val cameraPermissionLauncher =
        rememberCameraPermissionLauncher(
            onGrant = {
                val uri = context.createProfileImageUri()
                imageUri = uri
                cameraLauncher.launch(uri)
            },
            onDeny = { Toast.makeText(context, "카메라 권한이 필요합니다", Toast.LENGTH_SHORT).show() },
        )

    ButtonWithIcon(
        title = stringResource(R.string.attach_photo),
        isEnable = isEnable,
        modifier =
            Modifier
                .padding(
                    LanPetDimensions.Margin.medium,
                ),
        onClick = {
            scope.launch {
                sheetState.show()
            }
        },
    )

    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {},
            containerColor = Color.Transparent,
        ) {
            IOSActionSheet(
                cancelButton = {
                    ActionButton(
                        text = stringResource(R.string.title_button_close),
                        onClick = {
                            scope.launch {
                                sheetState.hide()
                            }
                        },
                    )
                },
                content = {
                    Column {
                        ActionButton(
                            text = stringResource(R.string.title_button_camera),
                            onClick = {
                                // TODO open camera
                                scope.launch {
                                    sheetState.hide()
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            },
                        )
                        ActionButton(
                            text = stringResource(R.string.title_button_gallery),
                            onClick = {
                                scope.launch {
                                    sheetState.hide()
                                    galleryLauncher.launch("image/*")
                                }
                            },
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun ButtonWithIcon(
    title: String,
    isEnable: Boolean,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Button(
        enabled = isEnable,
        shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
        onClick = {
            onClick?.invoke()
        },
        border =
            BorderStroke(
                1.dp,
                MaterialTheme.customColorScheme.buttonBackground,
            ),
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor = WhiteColor.White,
                contentColor = MaterialTheme.customColorScheme.buttonBackground,
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
    ) {
        Image(
            painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_camera),
            contentDescription = "ic_camera",
            modifier =
                Modifier
                    .padding(LanPetDimensions.Spacing.xxSmall)
                    .size(24.dp),
        )
        Text(
            title,
            style =
                MaterialTheme.customTypography().body2SemiBoldMulti.copy(
                    color = PrimaryColor.PRIMARY,
                ),
        )
    }
}

@Composable
private fun ImageWithDeleteIcon(
    uri: Uri,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Box(
        modifier.padding(LanPetDimensions.Spacing.xxSmall),
    ) {
        AsyncImage(
            model = uri,
            contentDescription = "post_image",
            contentScale = ContentScale.Crop,
            modifier =
                Modifier
                    .clip(
                        shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
                    ).size(82.dp),
            error = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_animals),
        )
        IconButton(
            modifier =
                Modifier
                    .padding(LanPetDimensions.Margin.xxSmall)
                    .align(Alignment.TopEnd)
                    .size(24.dp),
            onClick = {
                onClick.invoke()
            },
        ) {
            Image(
                painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_close_mini),
                contentDescription = "ic_close_mini",
            )
        }
    }
}

@Preview(heightDp = 1000)
@Composable
private fun FreeBoardWriteScreenLightPreview() {
    LanPetAppTheme {
        FreeBoardWriteScreen(
            onNavigateUp = {},
            freeBoardWriteViewModel = hiltViewModel(),
        )
    }
}

@Preview(heightDp = 1000, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FreeBoardWriteScreenDarkPreview() {
    LanPetAppTheme {
        FreeBoardWriteScreen(
            onNavigateUp = {},
        )
    }
}

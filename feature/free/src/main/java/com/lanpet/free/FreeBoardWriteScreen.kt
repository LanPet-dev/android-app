package com.lanpet.free

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.Close
import com.lanpet.core.common.widget.CommonCenterAlignedAppBarTitle
import com.lanpet.core.common.widget.CommonIconButtonBox
import com.lanpet.core.common.widget.CommonSubHeading1
import com.lanpet.core.common.widget.LanPetCenterAlignedTopAppBar
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.WhiteColor
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.domain.model.FreeBoardCategoryType
import com.lanpet.domain.model.PetCategory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FreeBoardWriteScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit = {},
) {
    val verticalScrollState = rememberScrollState()

    val imageList: List<String> = arrayListOf("")
    val completeEnable: Boolean = true

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
                actions = {
                    TextButton(
                        onClick = {},
                    ) {
                        Text(
                            text = stringResource(R.string.complete_action_freeboard_write),
                            style =
                                MaterialTheme.customTypography().body1SemiBoldSingle.copy(
                                    color =
                                        if (completeEnable) {
                                            GrayColor.Gray950
                                        } else {
                                            GrayColor.Gray300
                                        },
                                ),
                        )
                    }
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
                SelectBoardSection()
                LineWithSpacer()
                SelectPetSection()
                LineWithSpacer()
                TitleInputSection()
                ContentInputSection()
                ButtonWithIcon(
                    title = stringResource(R.string.complete_button_freeboard_write),
                    modifier =
                        Modifier
                            .padding(
                                LanPetDimensions.Margin.medium,
                            ),
                )
                LazyRow(
                    modifier = Modifier.padding(start = LanPetDimensions.Margin.small),
                ) {
                    items(imageList.size) { index ->
                        ImageWithDeleteIcon()
                    }
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
private fun SelectBoardSection(modifier: Modifier = Modifier) {
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
            SelectableChip.Rounded(
                title = FreeBoardCategoryType.COMMUNICATE.value,
                isSelected = false,
            ) { }
            SelectableChip.Rounded(
                title = FreeBoardCategoryType.RECOMMEND.value,
                isSelected = false,
            ) { }
            SelectableChip.Rounded(
                title = FreeBoardCategoryType.QUESTION.value,
                isSelected = false,
            ) { }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectPetSection(modifier: Modifier = Modifier) {
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
                    isSelected = false,
                ) { }
            }
        }
    }
}

@Composable
private fun TitleInputSection(modifier: Modifier = Modifier) {
    var input by rememberSaveable {
        mutableStateOf("")
    }
    Column(
        modifier =
            Modifier.padding(
                horizontal = LanPetDimensions.Margin.medium,
            ),
    ) {
        CommonSubHeading1(
            title = stringResource(R.string.title_title_input_freeboard_write),
        )
        Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xSmall))
        OutlinedTextField(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = LanPetDimensions.Margin.medium),
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
                input = newText
            },
            placeholder = {
                Text(
                    stringResource(R.string.title_title_input_freeboard_write),
                    style = MaterialTheme.typography.bodyMedium.copy(color = GrayColor.LIGHT),
                )
            },
        )
    }
}

@Composable
private fun ContentInputSection(modifier: Modifier = Modifier) {
    var input by rememberSaveable {
        mutableStateOf("")
    }

    val maxLength = 1000

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
                    text = "${input.length}/${String.format("%,d", maxLength)}자",
                    style = MaterialTheme.typography.bodySmall.copy(color = GrayColor.LIGHT),
                )
            }
        }
    }
}

@Composable
private fun ButtonWithIcon(
    title: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    Button(
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
            style = MaterialTheme.customTypography().body2SemiBoldMulti,
        )
    }
}

@Composable
private fun ImageWithDeleteIcon(modifier: Modifier = Modifier) {
    Box(
        modifier.padding(LanPetDimensions.Spacing.xxSmall),
    ) {
        if (false) {
            AsyncImage(
                // TODO model 수정 필요
                model = "",
                contentDescription = "post_image",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .clip(
                            shape = RoundedCornerShape(LanPetDimensions.Corner.xxSmall),
                        ).size(82.dp),
                error = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_animals),
            )
        } else {
            Image(
                painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.img_animals),
                contentDescription = "ic_animals",
                modifier = Modifier.size(82.dp),
            )
        }
        IconButton(
            modifier =
                Modifier
                    .padding(LanPetDimensions.Margin.xxSmall)
                    .align(Alignment.TopEnd)
                    .size(24.dp),
            onClick = {},
        ) {
            Image(
                painter = painterResource(id = com.lanpet.core.designsystem.R.drawable.ic_close_mini),
                contentDescription = "ic_close_mini",
            )
        }
    }
}

@Preview(heightDp = 1000)
@PreviewLightDark
@Composable
private fun FreeBoardWriteScreenPreview() {
    LanPetAppTheme {
        FreeBoardWriteScreen(
            onNavigateUp = {},
        )
    }
}

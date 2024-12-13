package com.lanpet.myprofile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.Close
import com.lanpet.core.common.widget.CommonAppBarTitle
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.CommonIconButtonBox
import com.lanpet.core.common.widget.CommonSubHeading1
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.common.widget.ProfileImageWithPicker
import com.lanpet.core.common.widget.SelectableChip
import com.lanpet.core.common.widget.TextFieldWithDeleteButton
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.domain.model.Age
import com.lanpet.myprofile.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyProfileAddProfileScreen(onClose: () -> Unit = { }) {
    val authViewModel = LocalAuthManager.current

    val verticalScrollState = rememberScrollState()

    Scaffold(
        topBar = {
            LanPetTopAppBar(
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
                        onClose,
                    )
                },
                title = {
                    CommonAppBarTitle(
                        title = stringResource(R.string.title_appbar_my_profile_add_profile),
                    )
                },
            )
        },
    ) {
        Surface(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(
                        horizontal = LanPetDimensions.Margin.Layout.horizontal,
                        vertical = LanPetDimensions.Margin.Layout.vertical,
                    ),
        ) {
            Column(
                modifier =
                    Modifier
                        .verticalScroll(
                            verticalScrollState,
                        ),
            ) {
                CommonSubHeading1(
                    title = stringResource(R.string.heading_my_profile_add_profile, "닉네임"),
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.large))
                ProfileImageWithPicker()
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.large))
                NickNameSection()
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                SelectAgeSection()
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                SelectPreferPetSection()
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                BioInputSection()
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                CommonButton(
                    title = stringResource(R.string.title_register_button),
                ) { }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
            }
        }
    }
}

@Composable
private fun BioInputSection() {
    var input by rememberSaveable {
        mutableStateOf("")
    }

    val maxLength = 200

    CommonSubHeading1(
        title = stringResource(R.string.bio_hint_my_profile_add_profile),
    )
    Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
    Box {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = input,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
            maxLines = 7,
            minLines = 7,
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
                    stringResource(R.string.bio_input_placeholder_my_profile_add_profile),
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
                text = "${input.length}/$maxLength",
                style = MaterialTheme.typography.bodySmall.copy(color = GrayColor.LIGHT),
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectPreferPetSection() {
    CommonSubHeading1(
        title = stringResource(R.string.prefer_pet_hint_my_profile_add_profile),
    )

    Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
    FlowRow {
        SelectableChip(
            title = "고양이",
            isSelected = false,
        ) { }
        SelectableChip(
            title = "강아지",
            isSelected = false,
        ) { }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SelectAgeSection() {
    CommonSubHeading1(
        title = stringResource(R.string.age_hint_my_profile_add_profile),
    )

    Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
    FlowRow {
        SelectableChip(
            title = Age.TENS.value,
            isSelected = false,
        ) { }
        SelectableChip(
            title = Age.TWENTIES.value,
            isSelected = false,
        ) { }
        SelectableChip(
            title = Age.THIRTIES.value,
            isSelected = false,
        ) { }
        SelectableChip(
            title = Age.FORTIES.value,
            isSelected = false,
        ) { }
        SelectableChip(
            title = Age.FIFTIES.value,
            isSelected = false,
        ) { }
    }
}

@Composable
private fun NickNameSection() {
    CommonSubHeading1(
        title = stringResource(R.string.nickname_hint_my_profile_add_profile),
    )
    Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
    TextFieldWithDeleteButton(
        value = "",
        onValueChange = { },
        placeholder = stringResource(R.string.nickname_placeholder_my_profile_add_profile),
    )
}

@Composable
@PreviewLightDark
fun MyProfileAddProfilePreview() {
    LanPetAppTheme {
        MyProfileAddProfileScreen()
    }
}

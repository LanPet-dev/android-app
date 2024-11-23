package com.example.profile.screen.nopet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.example.designsystem.theme.LanPetAppTheme
import com.example.designsystem.R as DS_R
import com.example.designsystem.theme.LanPetDimensions
import com.example.designsystem.theme.widgets.CommonButton
import com.example.designsystem.theme.widgets.LanPetTopAppBar
import com.example.profile.R
import com.example.profile.widget.Heading
import com.example.profile.widget.HeadingHint
import com.example.profile.widget.SelectableChip

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ProfileCreatePreferPetScreen() {
    Scaffold(
        topBar = {
            LanPetTopAppBar(
                title = {
                    Text(stringResource(R.string.appbar_title_profile_create))
                },
                actions = {
                    Text("3/4 ")
                }
            )
        },
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(
                    horizontal = LanPetDimensions.Margin.Layout.horizontal,
                    vertical = LanPetDimensions.Margin.Layout.vertical
                )
        ) {
            Column {
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                Heading(title = stringResource(R.string.heading_profile_create_prefer_pet_no_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
                HeadingHint(title = stringResource(R.string.sub_heading_profile_create_prefer_pet_no_pet))
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.medium))
                FlowRow {
                    SelectableChip(
                        title = "고양이",
                        isSelected = true
                    ) { }
                    SelectableChip(
                        title = "강아지",
                        isSelected = false
                    ) { }
                    SelectableChip(
                        title = "햄스터",
                        isSelected = false
                    ) { }
                    SelectableChip(
                        title = "도마뱀",
                        isSelected = false
                    ) { }
                    SelectableChip(
                        title = "앵무새",
                        isSelected = true
                    ) { }
                    SelectableChip(
                        title = "물고기",
                        isSelected = false
                    ) { }
                    SelectableChip(
                        title = "뱀",
                        isSelected = false
                    ) { }
                    SelectableChip(
                        title = "거미",
                        isSelected = false
                    ) { }
                    SelectableChip(
                        title = "양서류",
                        isSelected = false
                    ) { }
                    SelectableChip(
                        title = "기타",
                        isSelected = false
                    ) { }
                }
                Spacer(Modifier.weight(1f))
                CommonButton(title = stringResource(DS_R.string.next_button_string)) { }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Margin.xxSmall))
            }
        }
    }
}

@PreviewLightDark
@Composable
fun PreviewProfileCreatePreferPetScreen() {
    LanPetAppTheme {
        ProfileCreatePreferPetScreen()
    }
}

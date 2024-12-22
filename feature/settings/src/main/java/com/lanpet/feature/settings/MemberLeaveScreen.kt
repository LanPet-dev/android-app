package com.lanpet.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lanpet.core.auth.LocalAuthManager
import com.lanpet.core.common.MyIconPack
import com.lanpet.core.common.myiconpack.ArrowLeft
import com.lanpet.core.common.widget.ButtonSize
import com.lanpet.core.common.widget.CommonAppBarTitle
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.common.widget.CommonIconButtonBox
import com.lanpet.core.common.widget.LanPetTopAppBar
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.VioletColor
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemberLeaveScreen(
    modifier: Modifier = Modifier,
    onLeave: () -> Unit = {},
    onNavigateUp: () -> Unit = {},
) {
    val verticalScrollState = rememberScrollState()
    val profile by LocalAuthManager.current.defaultUserProfile.collectAsStateWithLifecycle()

    var reasonInput by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            LanPetTopAppBar(
                navigationIcon = {
                    CommonIconButtonBox(
                        content = {
                            Image(
                                imageVector = MyIconPack.ArrowLeft,
                                contentDescription = "ic_close",
                                colorFilter = ColorFilter.tint(MaterialTheme.customColorScheme.defaultIconColor),
                            )
                        },
                        onClick = onNavigateUp,
                    )
                },
                title = {
                    CommonAppBarTitle(
                        title = stringResource(R.string.title_appbar_member_leave),
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
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.medium))
                Text(
                    stringResource(R.string.header_member_leave, profile.nickname),
                    style = MaterialTheme.customTypography().title2SemiBoldMulti,
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.medium))
                // TODO("Satoshi"): Separate this info UI as a component
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(LanPetDimensions.Corner.xSmall))
                            .background(
                                color = VioletColor.Violet50,
                            ).padding(16.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(com.lanpet.core.common.R.drawable.ic_alert),
                            contentDescription = "ic_alert",
                            colorFilter = ColorFilter.tint(VioletColor.Violet900),
                            modifier = Modifier.size(24.dp),
                        )
                        Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
                        Text(
                            stringResource(R.string.alert_member_leave),
                            style = MaterialTheme.customTypography().body2RegularSingle.copy(color = VioletColor.Violet900),
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.large))
                Text(
                    stringResource(R.string.sub_header_reason_member_leave),
                    style = MaterialTheme.customTypography().title3SemiBoldSingle,
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.small))
                LeaveReasonInputSection(
                    input = reasonInput,
                    onTextChange = { reasonInput = it },
                )
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.large))
                CommonButton(
                    buttonSize = ButtonSize.LARGE,
                    title = stringResource(R.string.title_button_member_leave),
                    onClick = onLeave,
                )
            }
        }
    }
}

@Composable
fun LeaveReasonInputSection(
    input: String,
    modifier: Modifier = Modifier,
    onTextChange: (String) -> Unit = {},
) {
    val maxLength = 200

    Box {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = input,
            textStyle = MaterialTheme.typography.bodyMedium,
            shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
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
                    onTextChange(newText)
                }
            },
            placeholder = {
                Text(
                    stringResource(R.string.placeholder_reaseon_member_leave),
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

@Composable
@PreviewLightDark
private fun MemberLeaveScreenPreview() {
    LanPetAppTheme {
        MemberLeaveScreen()
    }
}

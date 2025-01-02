package com.lanpet.feature.settings

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.lanpet.core.common.widget.ButtonSize
import com.lanpet.core.common.widget.CommonButton
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun MemberLeaveCompleteScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {},
) {
    BackHandler {
        onLogout()
    }

    Scaffold {
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
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = stringResource(R.string.header_member_leave_complete),
                            style = MaterialTheme.customTypography().title2SemiBoldSingle,
                        )
                        Spacer(modifier = Modifier.height(LanPetDimensions.Spacing.small))
                        Text(
                            text = stringResource(R.string.sub_header_member_leave_complete),
                            style = MaterialTheme.customTypography().body2RegularSingle,
                        )
                    }
                }
                CommonButton(
                    buttonSize = ButtonSize.LARGE,
                    title = stringResource(R.string.title_button_member_leave_complete),
                    onClick = {
                        onLogout()
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = LanPetDimensions.Margin.Layout.horizontal,
                                vertical = LanPetDimensions.Margin.Layout.vertical,
                            ),
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
private fun MemberLeaveCompleteScreenPreview() {
    LanPetAppTheme {
        MemberLeaveCompleteScreen()
    }
}

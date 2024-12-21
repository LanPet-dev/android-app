package com.lanpet.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        shape = RoundedCornerShape(LanPetDimensions.Corner.medium),
        modifier = modifier,
    ) {
        Column(
            modifier =
                Modifier
                    .padding(
                        top = LanPetDimensions.Spacing.large,
                        start = LanPetDimensions.Spacing.large,
                        end = LanPetDimensions.Spacing.large,
                        bottom = LanPetDimensions.Spacing.medium,
                    ).fillMaxWidth(
                        0.9f,
                    ).background(
                        color = MaterialTheme.colorScheme.background,
                        shape =
                            RoundedCornerShape(
                                LanPetDimensions.Corner.medium,
                            ),
                    ),
        ) {
            Text(
                stringResource(R.string.dialog_logout_title),
                style = MaterialTheme.customTypography().body1SemiBoldSingle,
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xSmall))
            Text(
                stringResource(R.string.dialog_logout_content),
                style =
                    MaterialTheme.customTypography().body3RegularMulti.copy(
                        color = GrayColor.Gray700,
                    ),
            )
            Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                TextButton(
                    onClick = onDismiss,
                ) {
                    Text(
                        stringResource(R.string.dialog_logout_cancel),
                        style =
                            MaterialTheme.customTypography().body1SemiBoldSingle.copy(
                                color = GrayColor.Gray300,
                            ),
                    )
                }
                Spacer(modifier = Modifier.padding(LanPetDimensions.Spacing.xxSmall))
                TextButton(
                    onClick = onLogout,
                ) {
                    Text(
                        stringResource(R.string.dialog_logout_confirm),
                        style =
                            MaterialTheme.customTypography().body1SemiBoldSingle.copy(
                                color = PrimaryColor.PRIMARY,
                            ),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LogoutDialogPreview() {
    LogoutDialog(
        onDismiss = {},
        onLogout = {},
    )
}

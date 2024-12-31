package com.lanpet.myprofile.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.lanpet.core.common.widget.BaseDialog
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.customColorScheme
import com.lanpet.core.designsystem.theme.customTypography
import com.lanpet.myprofile.R

/**
 * 대표프로필 설정 다이얼로그
 */
@Composable
fun SetDefaultProfileDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSetDefaultProfile: () -> Unit = {},
) {
    BaseDialog(
        content = {
            Text(
                stringResource(R.string.dialog_set_default_profile_content),
                style =
                    MaterialTheme.customTypography().body1SemiBoldMulti.copy(
                        color = MaterialTheme.customColorScheme.textFieldText,
                    ),
            )
        },
        dismiss = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text(
                    stringResource(R.string.dialog_set_default_profile_dismiss),
                    style =
                        MaterialTheme.customTypography().body1SemiBoldSingle.copy(
                            color = GrayColor.Gray300,
                        ),
                )
            }
        },
        confirm = {
            TextButton(
                onClick = onSetDefaultProfile,
            ) {
                Text(
                    stringResource(R.string.dialog_set_default_profile_confirm),
                    style =
                        MaterialTheme.customTypography().body1SemiBoldSingle.copy(
                            color = PrimaryColor.PRIMARY,
                        ),
                )
            }
        },
    )
}

@Composable
@PreviewLightDark
private fun SetDefaultProfileDialogPreview() {
    LanPetAppTheme {
        Surface {
            SetDefaultProfileDialog()
        }
    }
}

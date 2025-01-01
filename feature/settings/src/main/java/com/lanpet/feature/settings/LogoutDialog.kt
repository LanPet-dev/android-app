package com.lanpet.feature.settings

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.lanpet.core.common.widget.BaseDialog
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.PrimaryColor
import com.lanpet.core.designsystem.theme.customTypography

@Composable
fun LogoutDialog(
    onDismiss: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BaseDialog(
        modifier = modifier,
        title = {
            Text(
                stringResource(R.string.dialog_logout_title),
                style = MaterialTheme.customTypography().body1SemiBoldSingle,
            )
        },
        dismiss = {
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
        },
        confirm = {
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
        },
        content = {
            Text(
                stringResource(R.string.dialog_logout_content),
                style =
                    MaterialTheme.customTypography().body3RegularMulti.copy(
                        color = GrayColor.Gray700,
                    ),
            )
        },
    )
}

@Preview(name = "Light")
@Preview(name = "Dark", uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL)
@Composable
private fun LogoutDialogPreview() {
    LanPetAppTheme {
        Surface {
            LogoutDialog(
                onDismiss = {},
                onLogout = {},
            )
        }
    }
}

package com.lanpet.core.common.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme

enum class ButtonSize {
    SMALL,
    MEDIUM,
    LARGE,
}

@Composable
fun CommonButton(
    title: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    buttonSize: ButtonSize = ButtonSize.MEDIUM,
    onClick: (() -> Unit)? = null,
) {
    when (buttonSize) {
        ButtonSize.SMALL -> {
            CommonButtonSmall(
                title = title,
                isActive = isActive,
                modifier = modifier,
                onClick = onClick,
            )
        }

        ButtonSize.MEDIUM -> {
            CommonButtonMedium(
                title = title,
                isActive = isActive,
                modifier = modifier,
                onClick = onClick,
            )
        }

        ButtonSize.LARGE -> {
            CommonButtonLarge(
                title = title,
                modifier = modifier,
                isActive = isActive,
                onClick = onClick,
            )
        }
    }
}

@Composable
internal fun CommonButtonSmall(
    title: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    Button(
        shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
        enabled = isActive,
        onClick = {
            onClick?.invoke()
        },
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor =
                    if (isActive) {
                        MaterialTheme.customColorScheme.buttonBackground
                    } else {
                        MaterialTheme.customColorScheme.buttonBackgroundDisabled
                    },
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight(),
    ) {
        Text(
            title,
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color =
                        if (isActive) {
                            MaterialTheme.customColorScheme.buttonText
                        } else {
                            MaterialTheme.customColorScheme.buttonTextDisabled
                        },
                ),
        )
    }
}

@Composable
internal fun CommonButtonMedium(
    title: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    Button(
        shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
        enabled = isActive,
        onClick = {
            onClick?.invoke()
        },
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor =
                    if (isActive) {
                        MaterialTheme.customColorScheme.buttonBackground
                    } else {
                        MaterialTheme.customColorScheme.buttonBackgroundDisabled
                    },
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .sizeIn(minHeight = 54.dp),
    ) {
        Text(
            title,
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color =
                        if (isActive) {
                            MaterialTheme.customColorScheme.buttonText
                        } else {
                            MaterialTheme.customColorScheme.buttonTextDisabled
                        },
                ),
        )
    }
}

@Composable
internal fun CommonButtonLarge(
    title: String,
    modifier: Modifier = Modifier,
    isActive: Boolean = true,
    onClick: (() -> Unit)? = null,
) {
    Button(
        shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
        enabled = isActive,
        onClick = {
            onClick?.invoke()
        },
        colors =
            ButtonDefaults.buttonColors().copy(
                containerColor =
                    if (isActive) {
                        MaterialTheme.customColorScheme.buttonBackground
                    } else {
                        MaterialTheme.customColorScheme.buttonBackgroundDisabled
                    },
            ),
        modifier =
            modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .sizeIn(minHeight = 64.dp),
    ) {
        Text(
            title,
            style =
                MaterialTheme.typography.bodyMedium.copy(
                    color =
                        if (isActive) {
                            MaterialTheme.customColorScheme.buttonText
                        } else {
                            MaterialTheme.customColorScheme.buttonTextDisabled
                        },
                ),
        )
    }
}

@PreviewLightDark
@Composable()
private fun PreviewCommonButton() {
    LanPetAppTheme {
        Column {
            CommonButtonSmall(
                title = "This is title",
                modifier = Modifier,
                onClick = {},
            )
            Spacer(modifier = Modifier.height(8.dp))
            CommonButtonMedium(
                title = "This is title",
                modifier = Modifier,
                onClick = {},
            )
            Spacer(modifier = Modifier.height(8.dp))
            CommonButtonLarge(
                title = "This is title",
                modifier = Modifier,
                onClick = {},
            )
            CommonButtonSmall(
                title = "This is title",
                isActive = false,
                modifier = Modifier,
                onClick = {},
            )
            Spacer(modifier = Modifier.height(8.dp))
            CommonButtonMedium(
                title = "This is title",
                isActive = false,
                modifier = Modifier,
                onClick = {},
            )
            Spacer(modifier = Modifier.height(8.dp))
            CommonButtonLarge(
                title = "This is title",
                isActive = false,
                modifier = Modifier,
                onClick = {},
            )
        }
    }
}

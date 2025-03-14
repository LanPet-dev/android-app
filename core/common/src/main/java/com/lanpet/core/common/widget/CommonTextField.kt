package com.lanpet.core.common.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetAppTheme
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme

@Composable
fun TextFieldWithDeleteButton(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit = {},
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(LanPetDimensions.Corner.xSmall),
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
        singleLine = singleLine,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyMedium.copy(color = GrayColor.LIGHT),
            )
        },
        trailingIcon = {
            DeleteButton(
                visible = value.isNotEmpty(),
                onClick = { onValueChange("") },
            )
        },
    )
}

@Composable
private fun DeleteButton(
    visible: Boolean,
    onClick: () -> Unit,
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear text",
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewTextFieldWithDeleteButton() {
    LanPetAppTheme {
        Column(Modifier.padding(16.dp)) {
            TextFieldWithDeleteButton(
                value = "value",
                onValueChange = {},
                placeholder = "placeholder",
                modifier = Modifier,
            )
            Spacer(Modifier.padding(8.dp))
            TextFieldWithDeleteButton(
                value = "",
                onValueChange = {},
                placeholder = "placeholder",
                modifier = Modifier,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewDeleteButton() {
    LanPetAppTheme {
        Column {
            DeleteButton(true) { }
            DeleteButton(false) { }
        }
    }
}

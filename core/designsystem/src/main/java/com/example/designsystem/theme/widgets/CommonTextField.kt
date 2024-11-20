package com.example.designsystem.theme.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import com.example.designsystem.theme.BlackColor
import com.example.designsystem.theme.GrayColor
import com.example.designsystem.theme.cornerRadiusLight

@Composable
fun TextFieldWithDeleteButton(
    value: String,
    placeholder: String,
    modifier: Modifier = Modifier.fillMaxWidth(),
    singleLine: Boolean = true,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        textStyle = MaterialTheme.typography.bodyMedium.copy(color = BlackColor.MEDIUM),
        shape = RoundedCornerShape(cornerRadiusLight),
        colors = OutlinedTextFieldDefaults.colors().copy(
            unfocusedIndicatorColor = GrayColor.LIGHT,
            focusedIndicatorColor = GrayColor.LIGHT,
            disabledIndicatorColor = GrayColor.LIGHT,
            focusedPlaceholderColor = GrayColor.MEDIUM,
            unfocusedPlaceholderColor = GrayColor.MEDIUM,
            disabledPlaceholderColor = GrayColor.MEDIUM,
            cursorColor = GrayColor.MEDIUM,
        ),
        singleLine = singleLine,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyMedium.copy(color = GrayColor.LIGHT)
            )
        },
        trailingIcon = {
            DeleteButton(
                visible = value.isNotEmpty(),
                onClick = { onValueChange("") }
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
        exit = fadeOut() + scaleOut()
    ) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear text"
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
fun PreviewTextFieldWithDeleteButton() {
    Column {
        TextFieldWithDeleteButton(
            value = "value",
            onValueChange = {},
            placeholder = "placeholder",
            modifier = Modifier
        )
        TextFieldWithDeleteButton(
            value = "",
            onValueChange = {},
            placeholder = "placeholder",
            modifier = Modifier
        )
    }
}

@Preview
@Composable
fun PreviewDeleteButton() {
    Column {
        DeleteButton(true) { }
        DeleteButton(false) { }
    }
}


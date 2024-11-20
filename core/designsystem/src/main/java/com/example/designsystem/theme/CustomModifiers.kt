package com.example.designsystem.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.crop(size: Dp = 130.dp, shape: Shape = CircleShape, onClick: () -> Unit) =
    Modifier
        .size(size)
        .clip(shape)
        .clickable(onClick = onClick)

fun Modifier.crop(size: Dp = 130.dp, shape: Shape = CircleShape) =
    Modifier
        .size(size)
        .clip(shape)

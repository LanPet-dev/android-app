package com.lanpet.core.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lanpet.core.designsystem.theme.GrayColor
import com.lanpet.core.designsystem.theme.LanPetDimensions
import com.lanpet.core.designsystem.theme.customColorScheme

fun Modifier.crop(size: Dp = 130.dp, shape: Shape = CircleShape, onClick: () -> Unit) =
    Modifier
        .size(size)
        .clip(shape)
        .clickable(onClick = onClick)

fun Modifier.crop(size: Dp = 130.dp, shape: Shape = CircleShape) =
    Modifier
        .size(size)
        .clip(shape)

fun Modifier.noRippleClickable(onClick: () -> Unit) = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}

fun Modifier.commonBorder(
    border: BorderStroke = BorderStroke(
        1.dp,
        GrayColor.LIGHT,
    ),
    shape: RoundedCornerShape = RoundedCornerShape(
        LanPetDimensions.Corner.xLarge,
    ),
) = composed {
    border(border, shape)
}
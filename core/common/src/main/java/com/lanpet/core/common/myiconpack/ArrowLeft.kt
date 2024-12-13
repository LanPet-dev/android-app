package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.ArrowLeft: ImageVector
    get() {
        if (arrowLeft != null) {
            return arrowLeft!!
        }
        arrowLeft =
            Builder(
                name = "Chevron-left",
                defaultWidth = 24.0.dp,
                defaultHeight =
                    24.0.dp,
                viewportWidth = 24.0f,
                viewportHeight = 24.0f,
            ).apply {
                group {
                    path(
                        fill = SolidColor(Color(0x00000000)),
                        stroke = SolidColor(Color(0xFF25262B)),
                        strokeLineWidth = 1.5f,
                        strokeLineCap = Round,
                        strokeLineJoin =
                            StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                        pathFillType =
                        NonZero,
                    ) {
                        moveTo(16.0f, 20.0f)
                        lineTo(8.0f, 12.0f)
                        lineTo(16.0f, 4.0f)
                    }
                }
            }.build()
        return arrowLeft!!
    }

private var arrowLeft: ImageVector? = null

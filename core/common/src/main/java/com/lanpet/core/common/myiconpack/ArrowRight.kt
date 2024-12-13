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

public val MyIconPack.ArrowRight: ImageVector
    get() {
        if (arrowRight != null) {
            return arrowRight!!
        }
        arrowRight =
            Builder(
                name = "Chevron-right", defaultWidth = 20.0.dp,
                defaultHeight =
                    20.0.dp,
                viewportWidth = 20.0f, viewportHeight = 20.0f,
            ).apply {
                group {
                    path(
                        fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF999999)),
                        strokeLineWidth = 2.0f, strokeLineCap = Round,
                        strokeLineJoin =
                            StrokeJoin.Companion.Round,
                        strokeLineMiter = 4.0f,
                        pathFillType =
                        NonZero,
                    ) {
                        moveTo(6.6668f, 16.6666f)
                        lineTo(13.3335f, 9.9999f)
                        lineTo(6.6668f, 3.3333f)
                    }
                }
            }
                .build()
        return arrowRight!!
    }

private var arrowRight: ImageVector? = null

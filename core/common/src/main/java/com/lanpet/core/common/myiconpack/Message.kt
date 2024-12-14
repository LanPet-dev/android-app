package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.Message: ImageVector
    get() {
        if (message != null) {
            return message!!
        }
        message =
            Builder(
                name = "Message", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f,
            ).apply {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero,
                ) {
                    moveTo(3.0f, 11.5f)
                    curveTo(2.9966f, 12.8199f, 3.3049f, 14.1219f, 3.9f, 15.3f)
                    curveTo(4.6056f, 16.7117f, 5.6902f, 17.8992f, 7.0325f, 18.7293f)
                    curveTo(8.3749f, 19.5594f, 9.9218f, 19.9994f, 11.5f, 20.0f)
                    curveTo(12.8199f, 20.0034f, 14.1219f, 19.6951f, 15.3f, 19.1f)
                    lineTo(21.0f, 21.0f)
                    lineTo(19.1f, 15.3f)
                    curveTo(19.6951f, 14.1219f, 20.0034f, 12.8199f, 20.0f, 11.5f)
                    curveTo(19.9994f, 9.9218f, 19.5594f, 8.3749f, 18.7293f, 7.0325f)
                    curveTo(17.8992f, 5.6902f, 16.7117f, 4.6056f, 15.3f, 3.9f)
                    curveTo(14.1219f, 3.3049f, 12.8199f, 2.9966f, 11.5f, 3.0f)
                    horizontalLineTo(11.0f)
                    curveTo(8.9157f, 3.115f, 6.947f, 3.9948f, 5.4709f, 5.4709f)
                    curveTo(3.9948f, 6.9469f, 3.115f, 8.9157f, 3.0f, 11.0f)
                    verticalLineTo(11.5f)
                    close()
                }
            }
                .build()
        return message!!
    }

private var message: ImageVector? = null

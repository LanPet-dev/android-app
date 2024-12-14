package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.Edit: ImageVector
    get() {
        if (edit != null) {
            return edit!!
        }
        edit =
            Builder(
                name = "Edit",
                defaultWidth = 24.0.dp,
                defaultHeight = 24.0.dp,
                viewportWidth = 24.0f,
                viewportHeight = 24.0f,
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF25262B)),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd,
                ) {
                    moveTo(3.9292f, 21.0711f)
                    curveTo(3.3769f, 21.0711f, 2.9292f, 20.6234f, 2.9292f, 20.0711f)
                    lineTo(2.9292f, 15.8284f)
                    curveTo(2.9292f, 15.5632f, 3.0346f, 15.3089f, 3.2221f, 15.1213f)
                    lineTo(15.2429f, 3.1005f)
                    curveTo(15.6334f, 2.71f, 16.2666f, 2.71f, 16.6571f, 3.1005f)
                    lineTo(20.8998f, 7.3432f)
                    curveTo(21.2903f, 7.7337f, 21.2903f, 8.3668f, 20.8998f, 8.7574f)
                    lineTo(8.879f, 20.7782f)
                    curveTo(8.6914f, 20.9657f, 8.4371f, 21.0711f, 8.1718f, 21.0711f)
                    horizontalLineTo(3.9292f)
                    close()
                    moveTo(7.7576f, 19.0711f)
                    lineTo(18.7784f, 8.0503f)
                    lineTo(15.95f, 5.2218f)
                    lineTo(4.9292f, 16.2426f)
                    verticalLineTo(19.0711f)
                    horizontalLineTo(7.7576f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFF25262B)),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd,
                ) {
                    moveTo(13.1216f, 6.636f)
                    lineTo(17.3642f, 10.8787f)
                    lineTo(15.95f, 12.2929f)
                    lineTo(11.7074f, 8.0503f)
                    lineTo(13.1216f, 6.636f)
                    close()
                }
            }.build()
        return edit!!
    }

private var edit: ImageVector? = null

package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.MyFill: ImageVector
    get() {
        if (myFill != null) {
            return myFill!!
        }
        myFill =
            Builder(
                name = "MyFill", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f,
            ).apply {
                path(
                    fill = SolidColor(Color(0xFF25262B)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(12.0f, 22.0f)
                    curveTo(17.5228f, 22.0f, 22.0f, 17.5228f, 22.0f, 12.0f)
                    curveTo(22.0f, 6.4771f, 17.5228f, 2.0f, 12.0f, 2.0f)
                    curveTo(6.4771f, 2.0f, 2.0f, 6.4771f, 2.0f, 12.0f)
                    curveTo(2.0f, 17.5228f, 6.4771f, 22.0f, 12.0f, 22.0f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero,
                ) {
                    moveTo(8.0f, 14.0f)
                    curveTo(8.0f, 14.0f, 9.5f, 16.0f, 12.0f, 16.0f)
                    curveTo(14.5f, 16.0f, 16.0f, 14.0f, 16.0f, 14.0f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero,
                ) {
                    moveTo(9.0f, 9.0f)
                    horizontalLineTo(9.01f)
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFFffffff)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero,
                ) {
                    moveTo(15.0f, 9.0f)
                    horizontalLineTo(15.01f)
                }
            }
                .build()
        return myFill!!
    }

private var myFill: ImageVector? = null

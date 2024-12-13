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

public val MyIconPack.Image: ImageVector
    get() {
        if (image != null) {
            return image!!
        }
        image =
            Builder(
                name = "Image", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f,
            ).apply {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero,
                ) {
                    moveTo(19.0f, 3.0f)
                    horizontalLineTo(5.0f)
                    curveTo(3.8954f, 3.0f, 3.0f, 3.8954f, 3.0f, 5.0f)
                    verticalLineTo(19.0f)
                    curveTo(3.0f, 20.1046f, 3.8954f, 21.0f, 5.0f, 21.0f)
                    horizontalLineTo(19.0f)
                    curveTo(20.1046f, 21.0f, 21.0f, 20.1046f, 21.0f, 19.0f)
                    verticalLineTo(5.0f)
                    curveTo(21.0f, 3.8954f, 20.1046f, 3.0f, 19.0f, 3.0f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero,
                ) {
                    moveTo(8.5f, 10.0f)
                    curveTo(9.3284f, 10.0f, 10.0f, 9.3284f, 10.0f, 8.5f)
                    curveTo(10.0f, 7.6716f, 9.3284f, 7.0f, 8.5f, 7.0f)
                    curveTo(7.6716f, 7.0f, 7.0f, 7.6716f, 7.0f, 8.5f)
                    curveTo(7.0f, 9.3284f, 7.6716f, 10.0f, 8.5f, 10.0f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero,
                ) {
                    moveTo(21.0f, 15.0f)
                    lineTo(16.0f, 10.0f)
                    lineTo(5.0f, 21.0f)
                }
            }
                .build()
        return image!!
    }

private var image: ImageVector? = null

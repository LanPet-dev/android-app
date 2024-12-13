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

public val MyIconPack.Bookmark: ImageVector
    get() {
        if (bookmark != null) {
            return bookmark!!
        }
        bookmark =
            Builder(
                name = "Bookmark", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f,
            ).apply {
                path(
                    fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round,
                    strokeLineJoin =
                        StrokeJoin.Companion.Round,
                    strokeLineMiter = 4.0f, pathFillType = NonZero,
                ) {
                    moveTo(19.0f, 21.0f)
                    lineTo(12.0f, 16.0f)
                    lineTo(5.0f, 21.0f)
                    verticalLineTo(5.0f)
                    curveTo(5.0f, 4.4696f, 5.2107f, 3.9609f, 5.5858f, 3.5858f)
                    curveTo(5.9609f, 3.2107f, 6.4696f, 3.0f, 7.0f, 3.0f)
                    horizontalLineTo(17.0f)
                    curveTo(17.5304f, 3.0f, 18.0391f, 3.2107f, 18.4142f, 3.5858f)
                    curveTo(18.7893f, 3.9609f, 19.0f, 4.4696f, 19.0f, 5.0f)
                    verticalLineTo(21.0f)
                    close()
                }
            }
                .build()
        return bookmark!!
    }

private var bookmark: ImageVector? = null

package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.CommunityFill: ImageVector
    get() {
        if (communityFill != null) {
            return communityFill!!
        }
        communityFill =
            Builder(
                name = "CommunityFill",
                defaultWidth = 24.0.dp,
                defaultHeight =
                    24.0.dp,
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
                    pathFillType = NonZero,
                ) {
                    moveTo(21.0f, 15.0f)
                    curveTo(21.0f, 15.5304f, 20.7893f, 16.0391f, 20.4142f, 16.4142f)
                    curveTo(20.0391f, 16.7893f, 19.5304f, 17.0f, 19.0f, 17.0f)
                    horizontalLineTo(7.0f)
                    lineTo(3.0f, 21.0f)
                    verticalLineTo(5.0f)
                    curveTo(3.0f, 4.4696f, 3.2107f, 3.9609f, 3.5858f, 3.5858f)
                    curveTo(3.9609f, 3.2107f, 4.4696f, 3.0f, 5.0f, 3.0f)
                    horizontalLineTo(19.0f)
                    curveTo(19.5304f, 3.0f, 20.0391f, 3.2107f, 20.4142f, 3.5858f)
                    curveTo(20.7893f, 3.9609f, 21.0f, 4.4696f, 21.0f, 5.0f)
                    verticalLineTo(15.0f)
                    close()
                }
                path(
                    fill = SolidColor(Color(0xFFffffff)),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(8.0f, 10.0f)
                    moveToRelative(-1.0f, 0.0f)
                    arcToRelative(1.0f, 1.0f, 0.0f, true, true, 2.0f, 0.0f)
                    arcToRelative(1.0f, 1.0f, 0.0f, true, true, -2.0f, 0.0f)
                }
                path(
                    fill = SolidColor(Color(0xFFffffff)),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(12.0f, 10.0f)
                    moveToRelative(-1.0f, 0.0f)
                    arcToRelative(1.0f, 1.0f, 0.0f, true, true, 2.0f, 0.0f)
                    arcToRelative(1.0f, 1.0f, 0.0f, true, true, -2.0f, 0.0f)
                }
                path(
                    fill = SolidColor(Color(0xFFffffff)),
                    stroke = null,
                    strokeLineWidth = 0.0f,
                    strokeLineCap = Butt,
                    strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f,
                    pathFillType = NonZero,
                ) {
                    moveTo(16.0f, 10.0f)
                    moveToRelative(-1.0f, 0.0f)
                    arcToRelative(1.0f, 1.0f, 0.0f, true, true, 2.0f, 0.0f)
                    arcToRelative(1.0f, 1.0f, 0.0f, true, true, -2.0f, 0.0f)
                }
            }.build()
        return communityFill!!
    }

private var communityFill: ImageVector? = null

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

public val MyIconPack.Like: ImageVector
    get() {
        if (_like != null) {
            return _like!!
        }
        _like = Builder(name = "Like", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
                viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF25262B)),
                    strokeLineWidth = 2.0f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(7.0f, 11.0f)
                lineTo(11.0f, 2.0f)
                curveTo(11.7956f, 2.0f, 12.5587f, 2.3161f, 13.1213f, 2.8787f)
                curveTo(13.6839f, 3.4413f, 14.0f, 4.2043f, 14.0f, 5.0f)
                verticalLineTo(9.0f)
                horizontalLineTo(19.66f)
                curveTo(19.9499f, 8.9967f, 20.2371f, 9.0565f, 20.5016f, 9.1752f)
                curveTo(20.7661f, 9.2939f, 21.0016f, 9.4687f, 21.1919f, 9.6875f)
                curveTo(21.3821f, 9.9063f, 21.5225f, 10.1638f, 21.6033f, 10.4423f)
                curveTo(21.6842f, 10.7207f, 21.7035f, 11.0134f, 21.66f, 11.3f)
                lineTo(20.28f, 20.3f)
                curveTo(20.2077f, 20.7769f, 19.9654f, 21.2116f, 19.5979f, 21.524f)
                curveTo(19.2304f, 21.8364f, 18.7623f, 22.0055f, 18.28f, 22.0f)
                horizontalLineTo(7.0f)
                moveTo(7.0f, 11.0f)
                verticalLineTo(22.0f)
                moveTo(7.0f, 11.0f)
                horizontalLineTo(4.0f)
                curveTo(3.4696f, 11.0f, 2.9609f, 11.2107f, 2.5858f, 11.5858f)
                curveTo(2.2107f, 11.9609f, 2.0f, 12.4696f, 2.0f, 13.0f)
                verticalLineTo(20.0f)
                curveTo(2.0f, 20.5304f, 2.2107f, 21.0391f, 2.5858f, 21.4142f)
                curveTo(2.9609f, 21.7893f, 3.4696f, 22.0f, 4.0f, 22.0f)
                horizontalLineTo(7.0f)
            }
        }
        .build()
        return _like!!
    }

private var _like: ImageVector? = null
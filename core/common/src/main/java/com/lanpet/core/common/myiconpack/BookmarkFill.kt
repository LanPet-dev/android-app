package com.lanpet.core.common.myiconpack

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.lanpet.core.common.MyIconPack

public val MyIconPack.BookmarkFill: ImageVector
    get() {
        if (_bookmarkFill != null) {
            return _bookmarkFill!!
        }
        _bookmarkFill = Builder(name = "BookmarkFill", defaultWidth = 24.0.dp, defaultHeight =
                24.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(0.0f, 0.0f)
                horizontalLineToRelative(24.0f)
                verticalLineToRelative(24.0f)
                horizontalLineToRelative(-24.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF25262B)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
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
        return _bookmarkFill!!
    }

private var _bookmarkFill: ImageVector? = null

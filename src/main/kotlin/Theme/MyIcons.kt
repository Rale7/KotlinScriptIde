package Theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ChromeClose: ImageVector
    get() {
        if (_ChromeClose != null) {
            return _ChromeClose!!
        }
        _ChromeClose = ImageVector.Builder(
            name = "ChromeClose",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(7.116f, 8f)
                lineToRelative(-4.558f, 4.558f)
                lineToRelative(0.884f, 0.884f)
                lineTo(8f, 8.884f)
                lineToRelative(4.558f, 4.558f)
                lineToRelative(0.884f, -0.884f)
                lineTo(8.884f, 8f)
                lineToRelative(4.558f, -4.558f)
                lineToRelative(-0.884f, -0.884f)
                lineTo(8f, 7.116f)
                lineTo(3.442f, 2.558f)
                lineToRelative(-0.884f, 0.884f)
                lineTo(7.116f, 8f)
                close()
            }
        }.build()
        return _ChromeClose!!
    }

private var _ChromeClose: ImageVector? = null

val ChromeMaximize: ImageVector
    get() {
        if (_ChromeMaximize != null) {
            return _ChromeMaximize!!
        }
        _ChromeMaximize = ImageVector.Builder(
            name = "ChromeMaximize",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3f, 3f)
                verticalLineToRelative(10f)
                horizontalLineToRelative(10f)
                verticalLineTo(3f)
                horizontalLineTo(3f)
                close()
                moveToRelative(9f, 9f)
                horizontalLineTo(4f)
                verticalLineTo(4f)
                horizontalLineToRelative(8f)
                verticalLineToRelative(8f)
                close()
            }
        }.build()
        return _ChromeMaximize!!
    }

private var _ChromeMaximize: ImageVector? = null
val ChromeMinimize: ImageVector
    get() {
        if (_ChromeMinimize != null) {
            return _ChromeMinimize!!
        }
        _ChromeMinimize = ImageVector.Builder(
            name = "ChromeMinimize",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14f, 8f)
                verticalLineToRelative(1f)
                horizontalLineTo(3f)
                verticalLineTo(8f)
                horizontalLineToRelative(11f)
                close()
            }
        }.build()
        return _ChromeMinimize!!
    }

private var _ChromeMinimize: ImageVector? = null

val ChromeRestore: ImageVector
    get() {
        if (_ChromeRestore != null) {
            return _ChromeRestore!!
        }
        _ChromeRestore = ImageVector.Builder(
            name = "ChromeRestore",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(3f, 5f)
                verticalLineToRelative(9f)
                horizontalLineToRelative(9f)
                verticalLineTo(5f)
                horizontalLineTo(3f)
                close()
                moveToRelative(8f, 8f)
                horizontalLineTo(4f)
                verticalLineTo(6f)
                horizontalLineToRelative(7f)
                verticalLineToRelative(7f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(5f, 5f)
                horizontalLineToRelative(1f)
                verticalLineTo(4f)
                horizontalLineToRelative(7f)
                verticalLineToRelative(7f)
                horizontalLineToRelative(-1f)
                verticalLineToRelative(1f)
                horizontalLineToRelative(2f)
                verticalLineTo(3f)
                horizontalLineTo(5f)
                verticalLineToRelative(2f)
                close()
            }
        }.build()
        return _ChromeRestore!!
    }

private var _ChromeRestore: ImageVector? = null

val AddIcon: ImageVector
    get() {
        if (_AddIcon != null) {
            return _AddIcon!!
        }
        _AddIcon = ImageVector.Builder(
            name = "Add",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(14f, 7f)
                verticalLineToRelative(1f)
                horizontalLineTo(8f)
                verticalLineToRelative(6f)
                horizontalLineTo(7f)
                verticalLineTo(8f)
                horizontalLineTo(1f)
                verticalLineTo(7f)
                horizontalLineToRelative(6f)
                verticalLineTo(1f)
                horizontalLineToRelative(1f)
                verticalLineToRelative(6f)
                horizontalLineToRelative(6f)
                close()
            }
        }.build()
        return _AddIcon!!
    }

private var _AddIcon: ImageVector? = null

public val DebugStart: ImageVector
    get() {
        if (_DebugStart != null) {
            return _DebugStart!!
        }
        _DebugStart = ImageVector.Builder(
            name = "DebugStart",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(4.25f, 3f)
                lineToRelative(1.166f, -0.624f)
                lineToRelative(8f, 5.333f)
                verticalLineToRelative(1.248f)
                lineToRelative(-8f, 5.334f)
                lineToRelative(-1.166f, -0.624f)
                verticalLineTo(3f)
                close()
                moveToRelative(1.5f, 1.401f)
                verticalLineToRelative(7.864f)
                lineToRelative(5.898f, -3.932f)
                lineTo(5.75f, 4.401f)
                close()
            }
        }.build()
        return _DebugStart!!
    }

private var _DebugStart: ImageVector? = null

public val DebugStop: ImageVector
    get() {
        if (_DebugStop != null) {
            return _DebugStop!!
        }
        _DebugStop = ImageVector.Builder(
            name = "DebugStop",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(13f, 1.99976f)
                lineTo(14f, 2.99976f)
                verticalLineTo(12.9998f)
                lineTo(13f, 13.9998f)
                horizontalLineTo(3f)
                lineTo(2f, 12.9998f)
                lineTo(2f, 2.99976f)
                lineTo(3f, 1.99976f)
                horizontalLineTo(13f)
                close()
                moveTo(12.7461f, 3.25057f)
                lineTo(3.25469f, 3.25057f)
                lineTo(3.25469f, 12.7504f)
                horizontalLineTo(12.7461f)
                verticalLineTo(3.25057f)
                close()
            }
        }.build()
        return _DebugStop!!
    }

private var _DebugStop: ImageVector? = null







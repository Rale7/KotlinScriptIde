package UI.customtitlebar

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import org.koin.compose.koinInject
import viewmodel.TitleBarViewModel

@Composable
fun CustomTitleBar(
    windowState: WindowState,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TitleBarViewModel = koinInject()
) {

    Box(
        modifier = modifier.pointerInput(Unit) {
            var prevChangeX = 0f
            var prevChangeY = 0f
            
            detectDragGestures(
                onDragStart = {
                    prevChangeX = it.x
                    prevChangeY = it.y
                },
                onDrag = { change, dragAmount ->
                    change.consume()

                    windowState.position = WindowPosition(
                        x = windowState.position.x + (change.position.x - prevChangeX).dp,
                        y = windowState.position.y + (change.position.y - prevChangeY).dp
                    )
                }
            )
        },
    ) {
        MyMenuBar(
            onOpenFolder = { viewModel.changeFolder() },
            viewModel = viewModel,
            modifier = Modifier.fillMaxWidth()
        )

        CustomTitleControls(
            windowState = windowState,
            onExit = onExit,
        )
    }
}




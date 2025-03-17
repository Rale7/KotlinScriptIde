package UI.customtitlebar

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
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
            detectDragGestures { change, dragAmount ->
                change.consume()
                windowState.position = WindowPosition(
                    x = windowState.position.x,
                    y = windowState.position.y
                )
            }
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




package com.kotlin.script.ide.UI.customtitlebar

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import org.koin.compose.koinInject
import com.kotlin.script.ide.viewmodel.TitleBarViewModel

@Composable
fun CustomTitleBar(
    windowState: WindowState,
    onExit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TitleBarViewModel = koinInject()
) {

    val state by viewModel.state.collectAsState()

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
            isMaximized = state.isMaximized,
            onRestore = viewModel::restoreWindow,
            onMaximize = viewModel::maximizeWindow,
            onExit = onExit,
        )
    }
}




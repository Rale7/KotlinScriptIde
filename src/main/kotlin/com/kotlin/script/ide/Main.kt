package com.kotlin.script.ide

import com.kotlin.script.ide.UI.customtitlebar.CustomTitleBar
import com.kotlin.script.ide.UI.editorpane.EditorPane
import com.kotlin.script.ide.UI.filelist.FileList
import com.kotlin.script.ide.UI.outputpane.OutputPane
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.runtime.*
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import com.kotlin.script.ide.Theme.surfaceA0
import com.kotlin.script.ide.Theme.tonalA0
import com.kotlin.script.ide.Theme.tonalA1
import com.kotlin.script.ide.Theme.tonalA2
import com.kotlin.script.ide.koin.appModule
import org.koin.core.context.startKoin
import java.awt.Cursor

@Composable
@Preview
fun App(
    windowState: WindowState,
    onClose: () -> Unit = {},
) {
    var leftPaneWidth by remember { mutableStateOf(300.dp) }
    var outputHeight by remember { mutableStateOf(400.dp) }

    Column {
        CustomTitleBar(
            windowState = windowState,
            onExit = onClose,
            modifier = Modifier.height(40.dp).background(
                color = surfaceA0,
                shape = RectangleShape
            )
        )

        Row {
            FileList(
                modifier = Modifier.fillMaxHeight()
                    .width(leftPaneWidth)
                    .background(color = tonalA2),
            )

            Box(modifier = Modifier.fillMaxHeight()
                .width(8.dp)
                .background(color = tonalA2)
                .pointerHoverIcon(PointerIcon(Cursor(Cursor.E_RESIZE_CURSOR)))
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        leftPaneWidth = (leftPaneWidth + dragAmount.dp).coerceIn(200.dp, 500.dp)
                    }
                }
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                EditorPane(
                    modifier = Modifier.background(tonalA1)
                        .fillMaxWidth()
                        .height(outputHeight),
                )

                Box(modifier = Modifier.fillMaxWidth()
                    .height(1.dp)
                    .background(color = tonalA0)
                    .pointerHoverIcon(PointerIcon(Cursor(Cursor.N_RESIZE_CURSOR)))
                    .pointerInput(Unit) {
                        detectVerticalDragGestures {  _, dragAmount ->
                            outputHeight = (outputHeight + dragAmount.dp).coerceIn(200.dp, 700.dp)
                        }
                    }
                )

                OutputPane(
                    modifier = Modifier.background(tonalA1)
                        .fillMaxSize(),
                )
            }
        }
    }
}

fun main() {

    startKoin {
        modules(appModule)
    }

    application {
        val windowState = remember { WindowState() }
        Window(
            onCloseRequest = ::exitApplication,
            undecorated = true,
            state = windowState,
        ) {
            App(
                windowState = windowState,
                onClose = ::exitApplication
            )
        }
    }
}

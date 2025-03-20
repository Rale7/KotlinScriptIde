package UI.customtitlebar

import Theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState

@Composable
fun CustomTitleControls(
    windowState: WindowState,
    isMaximized: Boolean,
    onMaximize: (WindowState) -> Unit,
    onRestore: (WindowState) -> Unit,
    onExit: () -> Unit,
) {

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().background(color = Color.Transparent)
    ) {
        StartStopControl()

        Spacer(modifier = Modifier.width(30.dp))

        ControlButton(
            onClick = {
                windowState.isMinimized = true
            },
            hoverColor = surfaceA1,
        ) {
            Icon(
                imageVector = ChromeMinimize,
                contentDescription = "Minimize",
                tint = Color.White
            )
        }

        ControlButton(
            onClick = {
                if (isMaximized) {
                    onRestore(windowState)
                } else {
                    onMaximize(windowState)
                }
            },
            hoverColor = surfaceA1,
        ) {
            Icon(
                imageVector = if (isMaximized) ChromeRestore else ChromeMaximize,
                contentDescription = "Maximize",
                tint = Color.White
            )
        }

        ControlButton(
            onClick = {
                onExit()
            },
            hoverColor = hoverRed
        ) {
            Icon(
                imageVector = ChromeClose,
                contentDescription = "Close",
                tint = Color.White
            )
        }
    }
}
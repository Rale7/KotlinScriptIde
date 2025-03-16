package UI.customtitlebar

import Theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowState

@Composable
fun CustomTitleControls(
    windowState: WindowState,
    onExit: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().background(color = Color.Transparent)
    ) {
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
                windowState.placement = if (windowState.placement == WindowPlacement.Maximized) {
                    WindowPlacement.Floating
                } else {
                    WindowPlacement.Maximized
                }
            },
            hoverColor = surfaceA1,
        ) {
            Icon(
                imageVector = if (windowState.placement == WindowPlacement.Maximized) ChromeRestore else ChromeMaximize,
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
package UI.customtitlebar

import Theme.DebugStart
import Theme.DebugStop
import Theme.hoverRed
import Theme.primaryA0
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import viewmodel.TerminalViewModel

@Composable
fun StartStopControl(
    viewModel: TerminalViewModel = koinInject()
) {
    val isRunning by viewModel.isRunning.collectAsState(initial = false)

    if (!isRunning) {
        Button(
            onClick = viewModel::runSelectedFile,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        ) {
            Icon(
                imageVector = DebugStart,
                contentDescription = "Start",
                tint = primaryA0,
                modifier = Modifier.size(30.dp)
            )
        }
    } else {
        Button(
            onClick = viewModel::endProcess,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
            ),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
        ) {
            Icon(
                imageVector = DebugStop,
                contentDescription = "Stop",
                tint = hoverRed,
                modifier = Modifier.size(30.dp)
            )
        }
    }

}
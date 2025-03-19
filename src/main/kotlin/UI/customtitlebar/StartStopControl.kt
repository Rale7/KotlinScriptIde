package UI.customtitlebar

import Theme.*
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.unit.dp
import data.SelectedFile
import org.koin.compose.koinInject
import viewmodel.TerminalViewModel

@Composable
fun StartStopControl(
    viewModel: TerminalViewModel = koinInject()
) {
    val isRunning by viewModel.isRunning.collectAsState(initial = false)
    val selectedFile by viewModel.file.collectAsState(initial = SelectedFile())

    if (!isRunning) {
        Button(
            onClick = viewModel::runSelectedFile,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent,
            ),
            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
            enabled = selectedFile.fileName.isNotEmpty(),
            modifier = Modifier.pointerHoverIcon(if (selectedFile.fileName.isNotEmpty()) PointerIcon.Hand else PointerIcon.Default),
        ) {
            Icon(
                imageVector = DebugStart,
                contentDescription = "Start",
                tint = if (selectedFile.fileName.isNotEmpty()) primaryA0 else tonalA3,
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
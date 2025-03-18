package UI.outputpane

import Theme.primaryA0
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import viewmodel.TerminalViewModel
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter

@Composable
fun Terminal(
    modifier: Modifier = Modifier,
    viewModel: TerminalViewModel = koinInject()
) {
    val output by viewModel.output.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()

    BasicTextField(
        modifier = modifier
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown) {
                    if (keyEvent.key == Key.Enter) {
                        viewModel.enterPressed()
                        true
                    } else false
                } else false
            },
        value = output,
        onValueChange = viewModel::userInput,
        readOnly = !isRunning,
        textStyle = TextStyle(
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            fontSize = 15.sp,
            textIndent = TextIndent.None,
        ),
        cursorBrush = SolidColor(Color.White),
    )
}
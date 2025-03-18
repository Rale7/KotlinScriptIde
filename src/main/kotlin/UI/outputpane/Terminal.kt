package UI.outputpane

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import viewmodel.TerminalViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Terminal(
    modifier: Modifier = Modifier,
    viewModel: TerminalViewModel = koinInject()
) {
    val output by viewModel.output.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    BasicTextField(
        modifier = modifier
            .onPreviewKeyEvent { keyEvent ->
                if (keyEvent.type == KeyEventType.KeyDown) {
                    if (keyEvent.key == Key.Enter) {
                        viewModel.enterPressed()
                        true
                    } else false
                } else false
            }.onPointerEvent(PointerEventType.Press) {pointerEvent ->
                pointerEvent.changes.firstOrNull()?.let { change ->
                    textLayoutResult?.let { layout ->
                        val offset = layout.getOffsetForPosition(change.position)

                        viewModel.errorPressed(offset)
                    }
                }
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
        onTextLayout = { textLayoutResult = it },
    )
}
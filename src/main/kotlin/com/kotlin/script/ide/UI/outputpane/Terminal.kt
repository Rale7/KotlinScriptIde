package com.kotlin.script.ide.UI.outputpane

import com.kotlin.script.ide.Theme.tonalA0
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.*
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.koinInject
import com.kotlin.script.ide.viewmodel.TerminalViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Terminal(
    modifier: Modifier = Modifier,
    viewModel: TerminalViewModel = koinInject()
) {
    val output by viewModel.output.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val terminalState by viewModel.terminalState.collectAsState()

    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    LaunchedEffect(output.text) {
        verticalScrollState.animateScrollTo(verticalScrollState.maxValue)
    }

    Box(
        modifier = modifier
    ) {
        BasicTextField(
            modifier = Modifier.fillMaxSize()
                .pointerHoverIcon(if (terminalState.isHovered) PointerIcon.Hand else PointerIcon.Text, overrideDescendants = true)
                .padding(horizontal =  10.dp)
                .padding(top = 5.dp, bottom = 20.dp)
                .onPreviewKeyEvent { keyEvent ->
                    if (keyEvent.type == KeyEventType.KeyDown) {
                        if (keyEvent.key == Key.Enter) {
                            viewModel.enterPressed()
                            true
                        } else false
                    } else false
                }.onPointerEvent(PointerEventType.Move) { pointerEvent ->
                    pointerEvent.changes.firstOrNull()?.let { change ->
                        terminalState.textLayoutResult?.let { layout ->
                            val adjustedPosition = Offset(
                                x = change.position.x + horizontalScrollState.value,
                                y = change.position.y + verticalScrollState.value
                            )
                            val offset = layout.getOffsetForPosition(adjustedPosition)
                            viewModel.changeHoverState(offset)
                        }
                    }

                }.onPointerEvent(PointerEventType.Press) { pointerEvent ->
                    pointerEvent.changes.firstOrNull()?.let { change ->
                        terminalState.textLayoutResult?.let { layout ->
                            val adjustedPosition = Offset(
                                x = change.position.x + horizontalScrollState.value,
                                y = change.position.y + verticalScrollState.value
                            )
                            val offset = layout.getOffsetForPosition(adjustedPosition)
                            viewModel.errorPressed(offset)
                        }
                    }
                }.horizontalScroll(horizontalScrollState)
                .verticalScroll(verticalScrollState)
                .semantics { contentDescription = "terminal" },
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
            onTextLayout = viewModel::changeTextLayout,
        )

        VerticalScrollbar(
            modifier = Modifier.fillMaxHeight()
                .align(Alignment.CenterEnd)
                .width(12.dp)
                .padding(2.dp),
            adapter = rememberScrollbarAdapter(verticalScrollState),
            style = ScrollbarStyle(
                minimalHeight = 0.dp,
                thickness = 8.dp,
                shape = RectangleShape,
                hoverDurationMillis = 300,
                unhoverColor = tonalA0.copy(alpha = 0.5f),
                hoverColor = tonalA0,
            )
        )

        HorizontalScrollbar(
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomStart)
                .height(12.dp)
                .padding(2.dp),
            adapter = rememberScrollbarAdapter(horizontalScrollState),
            style = ScrollbarStyle(
                minimalHeight = 0.dp,
                thickness = 8.dp,
                shape = RectangleShape,
                hoverDurationMillis = 300,
                unhoverColor = tonalA0.copy(alpha = 0.5f),
                hoverColor = tonalA0,
            )
        )
    }
}
package com.kotlin.script.ide.UI.editorpane

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kotlin.script.ide.Theme.surfaceA4
import com.kotlin.script.ide.Theme.surfaceA6
import com.kotlin.script.ide.Theme.tonalA0
import com.kotlin.script.ide.Theme.tonalA3
import com.kotlin.script.ide.data.SelectedFile
import com.kotlin.script.ide.transformation.CombinedTransformation

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CodingTextArea(
    selectedFile: SelectedFile,
    changeText: (TextFieldValue) -> Unit,
    moveCursorDown: () -> Unit,
    moveCursorUp: () -> Unit,
    onTextAreaPressed: (Int) -> Unit,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
) {
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    var boxHeight by remember { mutableStateOf(0) }
    var boxWidth by remember { mutableStateOf(0) }

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Box(
        modifier = modifier.onGloballyPositioned {
            boxHeight = it.size.height
            boxWidth = it.size.width
        }
    ) {
        val numberOfLines = selectedFile.content.text.lines().size
        val currentSubstr = selectedFile.content.text.substring(0, selectedFile.content.selection.start)
        val currentLine = currentSubstr.count { it == '\n' }
        val cursorHorizontal = selectedFile.content.selection.start - currentSubstr.lastIndexOf('\n').let {
            if (it == -1) 0 else it
        }
        val lineHeight = with(LocalDensity.current) {
            (15.sp.toPx() * 1.2f).toDp()
        }

        val characterWidth = with(LocalDensity.current) {
            (15.sp.toPx() * 0.6f).toDp()
        }

        LaunchedEffect(cursorHorizontal) {

            if (cursorHorizontal <= horizontalScrollState.value / characterWidth.value) {
                horizontalScrollState.animateScrollBy(-characterWidth.value)
            }

            if (cursorHorizontal > (horizontalScrollState.value + boxWidth - characterWidth.value) / characterWidth.value) {
                horizontalScrollState.animateScrollBy(characterWidth.value)
            }
        }

        LaunchedEffect(currentLine) {
            if (currentLine < verticalScrollState.value / lineHeight.value) {
                verticalScrollState.scrollBy(-lineHeight.value)
            }

            if (currentLine > (verticalScrollState.value + boxHeight - lineHeight.value) / lineHeight.value) {
                verticalScrollState.scrollBy(lineHeight.value)
            }
        }

        val highlightTextOffset = lineHeight * currentLine - verticalScrollState.value.dp

        if (highlightTextOffset >= -lineHeight) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .offset(y = if (highlightTextOffset >= 0.dp) highlightTextOffset else 0.dp)
                    .height(if (highlightTextOffset >= 0.dp) lineHeight else lineHeight + highlightTextOffset)
                    .background(
                        color = tonalA3.copy(alpha = 0.5f)
                    )
            )
        }

        Row {
            Column(
                modifier = Modifier.fillMaxHeight()
                    .width(40.dp)
                    .padding(start = 10.dp)
                    .background(color = Color.Transparent)
                    .verticalScroll(verticalScrollState)
                    .semantics { contentDescription = "numOfLines" },
            ) {

                repeat(numberOfLines) {
                    Text(
                        text = "${it + 1}",
                        color = if (it != currentLine) surfaceA4 else surfaceA6,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth().padding(end = 4.dp),
                        fontSize = 15.sp,
                        lineHeight = with(LocalDensity.current) {lineHeight.toSp()}
                    )
                }

            }

            BasicTextField(
                value = selectedFile.content,
                onValueChange = changeText,
                modifier = Modifier.fillMaxSize()
                    .focusRequester(focusRequester)
                    .padding(start = 10.dp)
                    .background(color = Color.Transparent)
                    .horizontalScroll(horizontalScrollState)
                    .verticalScroll(verticalScrollState)
                    .onPreviewKeyEvent { event ->
                        if (event.type == KeyEventType.KeyDown) {
                            when (event.key) {
                                Key.DirectionUp -> {
                                    moveCursorUp()
                                    true
                                }
                                Key.DirectionDown -> {
                                    moveCursorDown()
                                    true
                                }
                                else -> false
                            }
                        } else false
                    }.onPointerEvent(PointerEventType.Release) { pointerEvent ->
                        onPressTextArea(pointerEvent, textLayoutResult, horizontalScrollState, verticalScrollState, onTextAreaPressed)
                    }.onPointerEvent(PointerEventType.Press) { pointerEvent ->
                        onPressTextArea(pointerEvent, textLayoutResult, horizontalScrollState, verticalScrollState, onTextAreaPressed)
                    }.semantics { contentDescription = "codingTextArea" },
                textStyle = TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 15.sp,
                    textIndent = TextIndent.None,
                    lineHeight = with(LocalDensity.current) {lineHeight.toSp()}
                ),
                cursorBrush = SolidColor(Color.White),
                visualTransformation = CombinedTransformation(),
                onTextLayout = {textLayoutResult = it}
            )
        }

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

fun onPressTextArea(
    pointerEvent: PointerEvent,
    textLayoutResult: TextLayoutResult?,
    horizontalScrollState: ScrollState,
    verticalScrollState: ScrollState,
    onTextAreaPressed: (Int) -> Unit
) {
    pointerEvent.changes.firstOrNull()?.let { change ->
        textLayoutResult?.let { layout ->
            val adjustedPosition = Offset(
                x = change.position.x + horizontalScrollState.value,
                y = change.position.y + verticalScrollState.value
            )
            val offset = layout.getOffsetForPosition(adjustedPosition)
            onTextAreaPressed(offset)
        }
    }
}
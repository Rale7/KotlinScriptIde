package UI.editorpane

import Theme.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.SelectedFile
import text.transformation.CombinedTransformation
import text.transformation.SyntaxHighlightTransformation
import text.transformation.TabTransformation

@Composable
fun CodingTextArea(
    selectedFile: SelectedFile,
    changeText: (TextFieldValue) -> Unit,
    moveCursorDown: () -> Unit,
    moveCursorUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    Box(
        modifier = modifier
    ) {
        val numberOfLines = selectedFile.content.text.lines().size
        val currentLine = selectedFile.content.text.substring(0, selectedFile.content.selection.start).count { it == '\n' }
        val lineHeight = with(LocalDensity.current) {
            (15.sp.toPx() * 1.2f).toDp()
        }

        Box(
            modifier = Modifier.fillMaxWidth()
                .offset(y = lineHeight * currentLine)
                .height(lineHeight)
                .background(
                    color = tonalA3.copy(alpha = 0.5f)
                )
        )

        Row {
            Column(
                modifier = Modifier.fillMaxHeight()
                    .width(40.dp)
                    .padding(start = 10.dp)
                    .background(color = Color.Transparent)
                    .verticalScroll(verticalScrollState),
            ) {

                repeat(numberOfLines) {
                    Text(
                        text = "${it + 1}",
                        color = if (it != currentLine) surfaceA4 else surfaceA6,
                        fontFamily = FontFamily.Monospace,
                        textAlign = TextAlign.Right,
                        modifier = Modifier.fillMaxWidth().padding(end = 4.dp),
                        fontSize = 15.sp
                    )
                }

            }

            BasicTextField(
                value = selectedFile.content,
                onValueChange = changeText,
                modifier = Modifier.fillMaxSize()
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
                    },
                textStyle = TextStyle(
                    color = Color.White,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 15.sp,
                    textIndent = TextIndent.None,
                ),
                cursorBrush = SolidColor(Color.White),
                visualTransformation = CombinedTransformation()
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
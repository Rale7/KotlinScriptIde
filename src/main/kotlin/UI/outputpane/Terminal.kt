package UI.outputpane

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
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
    terminalViewModel: TerminalViewModel = koinInject()
) {
    val output by terminalViewModel.output.collectAsState()

    BasicTextField(
        modifier = modifier,
        value = output,
        onValueChange = {},
        readOnly = true,
        textStyle = TextStyle(
            color = Color.White,
            fontFamily = FontFamily.Monospace,
            fontSize = 15.sp,
            textIndent = TextIndent.None,
        ),
        cursorBrush = SolidColor(Color.White),
    )



}
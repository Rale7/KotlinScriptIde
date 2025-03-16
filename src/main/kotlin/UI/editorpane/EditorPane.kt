package UI.editorpane

import Theme.tonalA0
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily

@Composable
fun EditorPane(
    modifier: Modifier = Modifier,
) {


    Column(
        modifier = modifier
    ) {

        EditorTabs(
            modifier = Modifier.fillMaxWidth()
                .height(30.dp)
                .background(
                    color = tonalA0,
                )
        )

        CodingTextArea(
            modifier = Modifier.fillMaxWidth()
        )



    }

}
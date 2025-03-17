package data

import androidx.compose.ui.text.input.TextFieldValue

data class SelectedFile(
    public val fileName: String = "",
    public val content: TextFieldValue = TextFieldValue(""),
    public val directory: String = "",
)

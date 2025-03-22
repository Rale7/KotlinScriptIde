package com.kotlin.script.ide.data

import androidx.compose.ui.text.input.TextFieldValue

data class SelectedFile(
    val fileName: String = "",
    val content: TextFieldValue = TextFieldValue(""),
    val directory: String = "",
)

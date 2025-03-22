package com.kotlin.script.ide.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CombinedTransformation : VisualTransformation {
    private val tabTransformation = TabTransformation()
    private val syntaxHighlightTransformation = SyntaxHighlightTransformation()

    override fun filter(text: AnnotatedString): TransformedText {
        val tabTransformedText = tabTransformation.filter(text)
        return syntaxHighlightTransformation.filter(tabTransformedText.text)
    }
}
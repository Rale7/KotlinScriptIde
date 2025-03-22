package com.kotlin.script.ide.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class TabTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        TabOffsetMapping.text = text.text

        val transformedText = buildAnnotatedString {
            text.text.forEach {
                if (it == '\t') {
                    append(" ".repeat(4))
                } else {
                    append(it)
                }
            }
        }

        return TransformedText(transformedText, TabOffsetMapping())
    }
}
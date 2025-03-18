package text.transformation

import Theme.primaryA0
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class SyntaxHighlightTransformation : VisualTransformation {

    val keywords = listOf(
        "as", "break", "class", "continue",
        "do", "else", "false", "for", "fun", "if",
        "in", "interface", "is", "null",
        "object", "package", "return", "super", "this", "throw",
        "true", "try", "typealias",
        "typeof", "val", "var", "when", "while",
        )

    private fun processText(code: String) : AnnotatedString {
        val builder = AnnotatedString.Builder(code)

        val regex = "\\b(${keywords.joinToString("|")})\\b".toRegex()

        regex.findAll(code).forEach {
            builder.addStyle(
                style = SpanStyle(color = primaryA0, fontWeight = FontWeight.Bold),
                start = it.range.first,
                end = it.range.last + 1
            )
        }

        return builder.toAnnotatedString()
    }

    override fun filter(text: AnnotatedString): TransformedText {
        return TransformedText(
            processText(text.text),
            TabOffsetMapping()
        )
    }

}
package com.kotlin.script.ide.transformation

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import com.kotlin.script.ide.Theme.primaryA0
import org.junit.Test

class SyntaxHighlightTransformationTest {

    private val transformation = SyntaxHighlightTransformation()

    private fun checkStyle(text: String, match: String) : Boolean{

        val highlighted = transformation.filter(buildAnnotatedString { append(text) }).text
        val spans = highlighted.spanStyles

        val startIndex = highlighted.text.indexOf(match)
        val endIndex = startIndex + 3

        return spans.any {
            it.item.fontWeight == FontWeight.Bold && it.item.color == primaryA0 &&
                    it.start <= startIndex && it.end >= endIndex
        }
    }

    @Test
    fun testFilter() {
        assert(checkStyle("fun ", "fun"))
    }

    @Test
    fun testFilterBefore() {
        assert(checkStyle(" ;fun", "fun"))
    }

    @Test
    fun testFilterWord() {
        assert(!checkStyle(" funny", "fun"))
    }

    @Test
    fun testFilterWordBefore() {
        assert(!checkStyle("refund", "fun"))
    }

    @Test
    fun testFilterSpecialCharacterBefore() {
        assert(checkStyle("+fun", "fun"))
    }

    @Test
    fun testFilterSpecialCharacterAfter() {
        assert(checkStyle("fun+", "fun"))
    }
}
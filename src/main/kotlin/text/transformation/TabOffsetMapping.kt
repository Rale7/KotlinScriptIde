package text.transformation

import androidx.compose.ui.text.input.OffsetMapping

class TabOffsetMapping(private val tabSize: Int = 4) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return text.substring(0, if (offset > text.length) text.length else offset).replace("\t", " ".repeat(4)).length
    }

    override fun transformedToOriginal(offset: Int): Int {
        return text.substring(0, if (offset > text.length) text.length else offset).replace(" ".repeat(4), "\t").length
    }

    private fun countTabsBeforeOffset(offset: Int): Int {
        return text.substring(0, if (offset > text.length) text.length else offset).count { it == '\t' }
    }

    companion object {
        var text: String = ""
    }
}
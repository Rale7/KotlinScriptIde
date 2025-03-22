package com.kotlin.script.ide.transformation

import androidx.compose.ui.text.input.OffsetMapping

class TabOffsetMapping(private val tabSize: Int = 4) : OffsetMapping {
    override fun originalToTransformed(offset: Int): Int {
        return text.substring(0, if (offset > text.length) text.length else offset).replace("\t", " ".repeat(tabSize)).length
    }

    override fun transformedToOriginal(offset: Int): Int {
        return text.substring(0, if (offset > text.length) text.length else offset).replace(" ".repeat(tabSize), "\t").length
    }

    companion object {
        var text: String = ""
    }
}
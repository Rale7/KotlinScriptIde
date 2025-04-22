package com.kotlin.script.ide.viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.kotlin.script.ide.data.FolderFile
import com.kotlin.script.ide.repositories.TabsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.min

class EditorPaneViewModel(private val tabsRepository: TabsRepository) {

    val tabs = tabsRepository.tabFiles
    val selectedFile = tabsRepository.selectedFile

    fun switchTab(tab: FolderFile) {
        tabsRepository.changeSelectedFile(tab)
    }

    fun changeText(newText: TextFieldValue) {
        tabsRepository.changeText(newText)
    }

    val focusRequester = tabsRepository.focusRequester

    fun closeTab(tab: FolderFile) {
        val index = tabs.value.indexOf(tab)
        tabsRepository.removeTabFile(tab)
        if (tab.name == selectedFile.value.fileName) {
            tabsRepository.changeSelectedFile(
                if (tabs.value.size == 0) FolderFile()
                else if (index == 0) tabs.value.last()
                else tabs.value[index - 1]
            )
        }
    }

    fun moveCursorUp() {
        val text = selectedFile.value.content.text
        val cursorPos = selectedFile.value.content.selection.start

        val currentLineIndex = text.take(cursorPos).count { it == '\n' }
        if (currentLineIndex > 0) {

            val currentLineStart = text.lastIndexOf('\n', cursorPos - 1).takeIf { it >= 0 } ?: 0
            val prevLineStart = text.lastIndexOf('\n', currentLineStart - 1).takeIf { it >= 0 } ?: 0

            val cursorOffsetInLine = cursorPos - currentLineStart - 1
            val prevLineLength = currentLineStart - prevLineStart - 1
            val newCursorPos = prevLineStart + 1 + min(cursorOffsetInLine, prevLineLength)

            tabsRepository.changeCursor(selectedFile.value.content.copy(selection = TextRange(newCursorPos)))
        }
    }

    fun moveCursorDown() {
        val text = selectedFile.value.content.text
        val cursorPos = selectedFile.value.content.selection.start
        val lines = text.split("\n")

        val currentLineIndex = text.take(cursorPos).count { it == '\n' }
        if (currentLineIndex < lines.size - 1) {

            val currentLineStart = text.lastIndexOf('\n', cursorPos - 1).takeIf { it >= 0 } ?: 0
            val nextLineStart = text.indexOf('\n', cursorPos).takeIf { it >= 0 } ?: text.length

            val cursorOffsetInLine = cursorPos - currentLineStart - 1
            val nextLineLength = text.indexOf('\n', nextLineStart + 1).takeIf { it >= 0 }?.minus(nextLineStart) ?: (text.length - nextLineStart)
            val newCursorPos = nextLineStart + 1 + min(cursorOffsetInLine, nextLineLength)

            tabsRepository.changeCursor(selectedFile.value.content.copy(selection = TextRange(newCursorPos)))
        }
    }

    data class TextAreaState(
        val pressed: Boolean = false,
        val previousCursor: Int = 0
    )

    private val _state = MutableStateFlow(TextAreaState())

    fun onTextAreaPress(offset: Int) {
        tabsRepository.changeCursor(selectedFile.value.content.run {
            val tabsOffset = text.substring(0, if (offset > text.length) text.length else offset).count { it == '\t' } * 3
            _state.value = _state.value.copy(
                pressed = true,
                previousCursor = offset - tabsOffset
            )
            copy(
                selection = TextRange(
                    end = offset - tabsOffset,
                    start = offset - tabsOffset
                )
            )
        })
    }

    fun onTextRelease(offset: Int) {
        _state.value = _state.value.copy(pressed = false)
        tabsRepository.changeCursor(selectedFile.value.content.run {
            val tabsOffset = text.substring(0, if (offset > text.length) text.length else offset).count { it == '\t' } * 3
            copy(
                selection = TextRange(
                    end = offset - tabsOffset,
                    start = _state.value.previousCursor
                )
            )
        })
    }

    fun onTextMove(offset: Int) {
        if (_state.value.pressed) {
            tabsRepository.changeCursor(selectedFile.value.content.run {
                val tabsOffset = text.substring(0, if (offset > text.length) text.length else offset).count { it == '\t' } * 3
                copy(
                    selection = TextRange(
                        end = offset - tabsOffset,
                        start = _state.value.previousCursor
                    )
                )
            })
        }
    }

}
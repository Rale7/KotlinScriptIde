package viewmodel

import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repositories.TabsRepository
import repositories.TerminalRepository

class TerminalViewModel(
    private val terminalRepository: TerminalRepository,
    private val tabsRepository: TabsRepository
) {

    val file = tabsRepository.selectedFile

    data class TerminalState(
        val textLayoutResult: TextLayoutResult? = null,
        val isHovered: Boolean = false,
    )

    private val _terminalState = MutableStateFlow(TerminalState())
    val terminalState = _terminalState.asStateFlow()

    fun changeTextLayout(textLayoutResult: TextLayoutResult) {
        _terminalState.value = terminalState.value.copy(textLayoutResult = textLayoutResult)
    }

    fun changeHoverState(offset: Int) {
        _terminalState.value = terminalState.value.copy(isHovered = output.value.annotatedString
            .getStringAnnotations("ERROR", offset, offset)
            .isNotEmpty()
        )
    }

    val output = terminalRepository.output

    val isRunning = terminalRepository.isRunning

    fun runSelectedFile() = terminalRepository.runScript(
            directory =  tabsRepository.selectedFile.value.directory,
            fileName =  tabsRepository.selectedFile.value.fileName
        )


    fun endProcess() = terminalRepository.endProcess()

    fun userInput(text: TextFieldValue) = terminalRepository.userInput(text)

    fun enterPressed() = terminalRepository.writeUserInput()

    fun errorPressed(offset: Int) {
        if (terminalRepository.runningFile.value.name.isEmpty()) return

        CoroutineScope(Dispatchers.IO).launch {
            if (tabsRepository.selectedFile.value.fileName.isEmpty()
                || tabsRepository.selectedFile.value.fileName != terminalRepository.runningFile.value.name
            ) {
                tabsRepository.addTabFile(terminalRepository.runningFile.value)
                tabsRepository.changeSelectedFile(terminalRepository.runningFile.value)?.join()
            }

            terminalRepository.output.value.annotatedString.getStringAnnotations(
                tag = "ERROR",
                start = offset,
                end = offset,
            ).firstOrNull()?.item?.let {
                focusCodingArea(it)
            }
        }

    }

    private fun focusCodingArea(errorMessage: String) {

        val line = errorMessage.split(':')[0]
        val column = errorMessage.split(':')[1]
        val lines = tabsRepository.selectedFile.value.content.text.split('\n')
        var position = 0

        for (i in 0..line.toInt() - 2) {
            position += lines[i].length + 1
        }

        position += column.toInt()

        CoroutineScope(Dispatchers.Default).launch {
            tabsRepository.changeCursor(tabsRepository.selectedFile.value.content.copy(selection = TextRange(position)))
            delay(200)
            tabsRepository.focusRequester.requestFocus()
        }
    }

}
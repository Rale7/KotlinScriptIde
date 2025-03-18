package viewmodel

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import repositories.TabsRepository
import repositories.TerminalRepository

class TerminalViewModel(
    private val terminalRepository: TerminalRepository,
    private val tabsRepository: TabsRepository
) {

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
        CoroutineScope(Dispatchers.IO).launch {
            if (tabsRepository.selectedFile.value.fileName.isEmpty()
                || tabsRepository.selectedFile.value.fileName != terminalRepository.runningFile.value.name
            ) {

                tabsRepository.changeSelectedFile(terminalRepository.runningFile.value)?.join()
            }

            val annotations = terminalRepository.output.value.annotatedString.getStringAnnotations(
                tag = "ERROR",
                start = 0,
                end = terminalRepository.output.value.annotatedString.length,
            )
            annotations.firstOrNull { annotation ->
                offset in annotation.start..annotation.end
            }?.item?.let {
                focusCodingArea(it)
            }
        }

    }

    private val regex = Regex("""(?<=\.kts:)(\d+):(\d+)""")

    private fun focusCodingArea(errorMessage: String) {

        regex.find(errorMessage)?.let {
            val (line, column) = it.destructured
            val lines = tabsRepository.selectedFile.value.content.text.split('\n')
            var position = 0

            for (i in 0..line.toInt() - 2) {
                position += lines[i].length
            }

            position += column.toInt()

            CoroutineScope(Dispatchers.Default).launch {
                tabsRepository.changeCursor(tabsRepository.selectedFile.value.content.copy(selection = TextRange(position)))
                delay(200)
                tabsRepository.focusRequester.requestFocus()
            }
        }
    }
}
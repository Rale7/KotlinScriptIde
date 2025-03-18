package viewmodel

import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
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
}
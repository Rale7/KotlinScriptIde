package viewmodel

import repositories.TabsRepository
import repositories.TerminalRepository

class TerminalViewModel(
    private val terminalRepository: TerminalRepository,
    private val tabsRepository: TabsRepository
) {

    val output = terminalRepository.output

    val isRunning = terminalRepository.runningFile.value.name != ""

    fun runSelectedFile() {
        terminalRepository.runScript(
            directory =  tabsRepository.selectedFile.value.directory,
            fileName =  tabsRepository.selectedFile.value.fileName
        )
    }
}
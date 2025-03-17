package viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import data.FolderFile
import data.SelectedFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import repositories.TabsRepository

class EditorPaneViewModel(private val tabsRepository: TabsRepository) {

    val tabs = tabsRepository.tabFiles
    val selectedFile = tabsRepository.selectedFile

    fun switchTab(tab: FolderFile) {
        tabsRepository.changeSelectedFile(tab)
    }

    fun changeText(newText: TextFieldValue) {
        tabsRepository.changeText(newText)
    }

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

}
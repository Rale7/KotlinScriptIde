package viewmodel

import androidx.compose.runtime.collectAsState
import data.AppState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

class TitleBarViewModel(private val appState: AppState) {

    data class TitleBarState(
        val fileExpanded: Boolean = false,
    )

    private val _state = MutableStateFlow(TitleBarState())
    val state = _state.asStateFlow()

    fun showFileMenu() {
        _state.value = _state.value.copy(fileExpanded = true)
    }

    fun hideFileMenu() {
        _state.value = _state.value.copy(fileExpanded = false)
    }

    fun changeFolder() {
        hideFileMenu()
        val fileChooser = JFileChooser(FileSystemView.getFileSystemView())
        fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY

        val result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            val folderPath = fileChooser.selectedFile.absolutePath
            appState.changeFolder(folderPath)
        }
    }

}
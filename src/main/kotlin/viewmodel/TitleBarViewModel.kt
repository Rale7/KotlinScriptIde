package viewmodel

import androidx.compose.runtime.collectAsState
import data.AppState
import kotlinx.coroutines.flow.StateFlow
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

class TitleBarViewModel(private val appState: AppState) {

    fun changeFolder() {
        val fileChooser = JFileChooser(FileSystemView.getFileSystemView())
        fileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY

        val result = fileChooser.showOpenDialog(null)
        if (result == JFileChooser.APPROVE_OPTION) {
            val folderPath = fileChooser.selectedFile.absolutePath
            appState.changeFolder(folderPath)
        }
    }

}
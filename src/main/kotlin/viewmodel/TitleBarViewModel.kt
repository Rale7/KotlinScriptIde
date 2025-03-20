package viewmodel

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import repositories.FolderRepository
import repositories.TabsRepository
import java.awt.GraphicsEnvironment
import javax.swing.JFileChooser
import javax.swing.filechooser.FileSystemView

class TitleBarViewModel(
    private val folderRepository: FolderRepository,
    private val tabsRepository: TabsRepository
) {

    data class TitleBarState(
        val fileExpanded: Boolean = false,
        val isMaximized: Boolean = false,
        val windowPosition: WindowPosition = WindowPosition(0.dp, 0.dp),
        val windowSize: DpSize = DpSize(0.dp, 0.dp)
    )

    private val _state = MutableStateFlow(TitleBarState())
    val state = _state.asStateFlow()

    private val _screenBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds

    fun maximizeWindow(windowState: WindowState) {
        _state.value = state.value.copy(
            windowPosition = windowState.position,
            windowSize = windowState.size,
            isMaximized = true
        )
        windowState.size = DpSize(_screenBounds.width.dp, _screenBounds.height.dp)
        windowState.position = WindowPosition(0.dp, 0.dp)
    }

    fun restoreWindow(windowState: WindowState) {
        _state.value = state.value.copy(
            isMaximized = false,
        )
        windowState.size = state.value.windowSize
        windowState.position = state.value.windowPosition
    }

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
            folderRepository.changeFolder(folderPath)
            tabsRepository.clearTabs()
        }
    }

}
package viewmodel

import data.AppState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class FileListViewModel(private val appState: AppState) {

    data class FileListState (
        val showDialog: Boolean = false,
        val newFileName: String = "",
        val isError: Boolean = false,
    )

    private val _state = MutableStateFlow(FileListState())
    val state = _state.asStateFlow()

    val path = appState.folder
    val files = appState.files

    fun showDialog() {
        _state.value = state.value.copy(showDialog = true)
    }

    fun hideDialog() {
        _state.value = state.value.copy(showDialog = false)
    }

    fun changeFileName(newFileName: String) {
        _state.value = state.value.copy(newFileName = newFileName)
    }

    fun createNewFile() {
        CoroutineScope(Dispatchers.IO).launch {
            if (!File(path.value.path, "${_state.value.newFileName}.kt").createNewFile()) {
                withContext(Dispatchers.Default) {
                    _state.value = state.value.copy(isError = true)
                }
            } else {
                withContext(Dispatchers.Default) {
                    hideDialog()
                }
            }
        }
    }

}
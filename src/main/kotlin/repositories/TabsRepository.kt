package repositories

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.TextFieldValue
import data.FolderFile
import data.SelectedFile
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.File

class TabsRepository {

    private val _tabFiles = MutableStateFlow(listOf<FolderFile>())
    val tabFiles = _tabFiles.asStateFlow()

    private val _selectedFile = MutableStateFlow(SelectedFile())
    val selectedFile = _selectedFile.asStateFlow()

    private var saveJob: Job? = null

    fun addTabFile(folderFile: FolderFile) {
        if (!_tabFiles.value.any { it.name == folderFile.name }) {
            _tabFiles.value = tabFiles.value.plus(folderFile)
        }

        changeSelectedFile(folderFile)
    }

    fun removeTabFile(folderFile: FolderFile) {
        _tabFiles.value = _tabFiles.value.filter { it.name != folderFile.name }
    }

    fun clearTabs() {
        _tabFiles.value = emptyList()
    }

    fun changeSelectedFile(folderFile: FolderFile) : Job? {
        _selectedFile.value = selectedFile.value.copy(fileName = folderFile.name, directory = folderFile.directory)
        return loadFileContent()
    }

    fun loadFileContent() =
        if (selectedFile.value.fileName.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                val txt = File(selectedFile.value.directory, selectedFile.value.fileName).readText(Charsets.UTF_8)

                withContext(Dispatchers.Default) {
                    _selectedFile.value = selectedFile.value.copy(content = TextFieldValue(txt))
                }
            }
        } else null


    fun changeText(newText: TextFieldValue) {
        _selectedFile.value = selectedFile.value.copy(content = newText)
        saveFile()
    }

    fun changeCursor(newCursor: TextFieldValue) {
        _selectedFile.value = selectedFile.value.copy(content = newCursor)
    }

    val focusRequester = FocusRequester()

    /**
     *  To minimize IO operations, we will try to write to file when
     *  user stops typing, also bufferedWriter is used for less disk
     *  operatons
     */
    fun saveFile() {
        saveJob?.cancel()

        saveJob = CoroutineScope(Dispatchers.IO).launch {
            delay(500)
            File(selectedFile.value.directory, selectedFile.value.fileName).bufferedWriter().use {
                it.write(selectedFile.value.content.text)
            }
        }
    }
}
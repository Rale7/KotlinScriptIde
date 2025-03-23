package com.kotlin.script.ide.viewmodel

import com.kotlin.script.ide.data.FolderFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.kotlin.script.ide.repositories.FolderRepository
import com.kotlin.script.ide.repositories.TabsRepository
import java.io.File

class FileListViewModel(
    folderRepository: FolderRepository,
    private val tabsRepository: TabsRepository
) {

    data class FileListState (
        val showDialog: Boolean = false,
        val newFileName: String = "",
        val isError: Boolean = false,
        val newFileLabel: String = "New file"
    )

    private val _state = MutableStateFlow(FileListState())
    val state = _state.asStateFlow()

    val path = folderRepository.folder
    val files = folderRepository.files

    fun showDialog() {
        _state.value = state.value.copy(
            showDialog = true,
            isError = false,
            newFileLabel = "New file",
            newFileName = "",
        )
    }

    fun hideDialog() {
        _state.value = state.value.copy(showDialog = false)
    }

    fun changeFileName(newFileName: String) {
        if (newFileName.length > 50) {
            _state.value = state.value.copy(
                isError = true,
                newFileLabel = "File name too long"
            )
        } else {
            _state.value = state.value.copy(
                newFileName = newFileName,
                isError = false,
                newFileLabel = "New file"
            )
        }
    }

    fun createNewFile() {
        if (state.value.newFileName.isEmpty()) {
            _state.value = state.value.copy(
                isError = true,
                newFileLabel = "File name cannot be empty"
            )

            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            if (!File(path.value.path, "${_state.value.newFileName}.kts").createNewFile()) {
                withContext(Dispatchers.Default) {
                    _state.value = state.value.copy(
                        isError = true,
                        newFileLabel = "File already exists"
                    )
                }
            } else {
                withContext(Dispatchers.Default) {
                    hideDialog()
                }
            }
        }
    }

    fun addFileToTab(folderFile: FolderFile) {
        tabsRepository.addTabFile(folderFile)
    }
}
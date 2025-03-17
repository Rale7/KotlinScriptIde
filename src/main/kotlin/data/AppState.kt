package data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds

class AppState {

    private val _folder = MutableStateFlow(Folder())
    val folder = _folder.asStateFlow()

    private val _files = MutableStateFlow(listOf<FolderFile>())
    val files = _files.asStateFlow()


    fun changeFolder(path: String) {
        _folder.value = folder.value.copy(path = path)

        CoroutineScope(Dispatchers.IO).launch {
            watchDirectory(path).collect {
                withContext(Dispatchers.Default) {
                    _files.value = it
                }
            }
        }

    }

    private fun watchDirectory(path: String) : Flow<List<FolderFile>> = flow {

        val folder = Paths.get(path)
        val watchService = FileSystems.getDefault().newWatchService()

        folder.register(
            watchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_MODIFY
        )

        emit(
            File(path).listFiles()
                ?.toList()
                ?.filter { it.isFile && (it.name.endsWith("kts") || it.name.endsWith("kt")) }
                ?.mapNotNull { FolderFile(it.name) } ?: emptyList())

        while (true) {
            val key = watchService.take()
            emit(
                File(path).listFiles()
                    ?.toList()
                    ?.filter { it.isFile && (it.name.endsWith("kts") || it.name.endsWith("kt")) }
                    ?.mapNotNull { FolderFile(it.name) } ?: emptyList())

            key.pollEvents()
            key.reset()
        }

    }
        .flowOn(Dispatchers.IO)

}

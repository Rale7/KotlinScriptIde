package repositories

import data.Folder
import data.FolderFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds

class FolderRepository {

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
                ?.filter { it.isFile && it.name.endsWith("kts") }
                ?.mapNotNull { FolderFile(name = it.name, directory = path) } ?: emptyList())

        while (true) {
            val key = watchService.take()
            emit(
                File(path).listFiles()
                    ?.toList()
                    ?.filter { it.isFile && it.name.endsWith("kts") }
                    ?.mapNotNull { FolderFile(name = it.name, directory = path) } ?: emptyList())

            key.pollEvents()
            key.reset()
        }

    }.flowOn(Dispatchers.IO)

}
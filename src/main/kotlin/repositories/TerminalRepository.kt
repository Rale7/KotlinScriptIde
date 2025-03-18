package repositories

import data.FolderFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.nio.file.Paths

class TerminalRepository {

    private val _runningFile = MutableStateFlow(FolderFile())
    val runningFile = _runningFile.asStateFlow()

    private val _output = MutableStateFlow("")
    val output = _output.asStateFlow()

    fun runScript(directory: String, fileName: String) {
        val fullPath = Paths.get(directory, fileName).toString()
        _runningFile.value = FolderFile(name = fileName, directory = directory)
        _output.value = "Starting script $fileName...\n\n"

        CoroutineScope(Dispatchers.IO).launch {

            val process = ProcessBuilder("cmd", "/c", "kotlinc", "-script", fullPath).start()
            val inputStream = BufferedReader(InputStreamReader(process.inputStream))
            val errorStream = BufferedReader(InputStreamReader(process.errorStream))
            val outputStream = BufferedWriter(OutputStreamWriter(process.outputStream))

            launch {
                inputStream.lineSequence().forEach {
                    withContext(Dispatchers.Default) {
                        _output.value = output.value.plus(it)
                    }
                }
            }

            launch {
                errorStream.lineSequence().forEach {
                    withContext(Dispatchers.Default) {
                        _output.value = output.value.plus("[ERROR] $it")
                    }
                }
            }

            val status = process.waitFor()
            withContext(Dispatchers.Default) {
                _output.value = output.value.plus("\n\nProcess finished with exit code $status")
            }

        }


    }


}
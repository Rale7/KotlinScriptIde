package repositories

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.TextFieldValue
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
import java.lang.Thread.yield
import java.nio.Buffer
import java.nio.file.Paths

class TerminalRepository {

    private val _runningFile = MutableStateFlow(FolderFile())
    val runningFile = _runningFile.asStateFlow()

    private val _isRunning = MutableStateFlow(false)
    val isRunning = _isRunning.asStateFlow()

    private val _output = MutableStateFlow(TextFieldValue(""))
    val output = _output.asStateFlow()

    var lastValidCursorPosition = 0

    private var process : Process? = null
    private var outputStream: BufferedWriter? = null

    fun runScript(directory: String, fileName: String) {
        val fullPath = Paths.get(directory, fileName).toString()
        _runningFile.value = FolderFile(name = fileName, directory = directory)
        _output.value = TextFieldValue("Starting script $fileName...\n\n")
        _isRunning.value = true

        CoroutineScope(Dispatchers.IO).launch {

            process = ProcessBuilder("cmd", "/c", "kotlinc", "-script", fullPath).start()
            val inputStream = process?.let { BufferedReader(InputStreamReader(it.inputStream)) }
            val errorStream = process?.let { BufferedReader(InputStreamReader(it.errorStream)) }
            outputStream = process?.outputStream?.let { OutputStreamWriter(it) }?.let { BufferedWriter(it) }

            launch {
                inputStream?.charSequence()?.forEach {
                    withContext(Dispatchers.Default) {
                        val newText = output.value.text.plus(it)
                        lastValidCursorPosition = newText.length

                        _output.value = _output.value.copy(
                            text = newText,
                            selection = TextRange(newText.length)
                        )
                    }
                }
            }

            launch {
                errorStream?.charSequence()?.forEach {
                    withContext(Dispatchers.Default) {
                        val newText = output.value.text.plus(it)
                        lastValidCursorPosition = newText.length

                        _output.value = _output.value.copy(
                            text = newText,
                            selection = TextRange(newText.length)
                        )
                    }
                }
            }

            val status = process?.waitFor()
            withContext(Dispatchers.Default) {
                _output.value = output.value.copy(
                    text = output.value.text.plus("\n\nProcess finished with exit code $status"),
                )
                _isRunning.value = false
            }
        }
    }

    fun endProcess() {
        process?.destroy()
    }

    fun userInput(text: TextFieldValue) {
        if (text.selection.end < lastValidCursorPosition && text.text.length != output.value.text.length) {
            if (text.text.length < output.value.text.length) {

                val newText = output.value.text.dropLast(1)
                _output.value = output.value.copy(
                    text = newText,
                    selection = TextRange(newText.length)
                )
            } else {

                val newText = output.value.text.plus(text.text.get(text.selection.end - 1))
                _output.value = output.value.copy(
                    text = newText,
                    selection = TextRange(newText.length)
                )
            }
        } else {
            _output.value = text
        }
    }

    fun writeUserInput() {
        outputStream?.write(output.value.text.substring(lastValidCursorPosition, output.value.text.length).plus('\n'))
        outputStream?.flush()
        _output.value = output.value.copy(
            text = output.value.text.plus("\n"),
            selection = TextRange(output.value.text.length)
        )
        lastValidCursorPosition = output.value.text.length
    }
}

fun BufferedReader.charSequence() : Sequence<Char> = sequence {
    while(true) {
        val char = read()
        if (char == -1) break
        yield(char.toChar())
    }
}
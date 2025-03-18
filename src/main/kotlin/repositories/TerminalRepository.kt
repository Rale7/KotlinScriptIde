package repositories

import Theme.hoverRed
import Theme.primaryA0
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
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

    private val _output = MutableStateFlow(TextFieldValue(AnnotatedString("")))
    val output = _output.asStateFlow()

    var lastValidCursorPosition = 0

    private var process : Process? = null
    private var outputStream: BufferedWriter? = null

    fun runScript(directory: String, fileName: String) {
        val fullPath = Paths.get(directory, fileName).toString()
        _runningFile.value = FolderFile(name = fileName, directory = directory)
        _output.value = TextFieldValue(buildAnnotatedString {
                append("Starting script $fileName...\n\n")
        })
        _isRunning.value = true

        CoroutineScope(Dispatchers.IO).launch {

            process = ProcessBuilder("cmd", "/c", "kotlinc", "-script", fullPath).start()
            val inputStream = process?.let { BufferedReader(InputStreamReader(it.inputStream)) }
            val errorStream = process?.let { BufferedReader(InputStreamReader(it.errorStream)) }
            outputStream = process?.outputStream?.let { OutputStreamWriter(it) }?.let { BufferedWriter(it) }

            launch {
                inputStream?.charSequence()?.forEach {
                    withContext(Dispatchers.Default) {
                        val newText = buildAnnotatedString {
                            append(output.value.annotatedString)
                            append(it)
                        }
                        lastValidCursorPosition = newText.length

                        _output.value = _output.value.copy(
                            annotatedString = newText,
                            selection = TextRange(newText.length)
                        )
                    }
                }
            }

            launch {
                errorStream?.lineSequence()?.forEach {
                    withContext(Dispatchers.Default) {
                        val newText = buildAnnotatedString {
                            append(output.value.annotatedString)
                            pushStringAnnotation(tag = "ERROR", annotation = it)
                            withStyle(style = SpanStyle(
                                color = hoverRed,
                                textDecoration = TextDecoration.Underline
                            )) {
                                append(it)
                            }
                            pop()
                        }
                        lastValidCursorPosition = newText.length

                        _output.value = _output.value.copy(
                            annotatedString = newText,
                            selection = TextRange(newText.length)
                        )
                    }
                }
            }

            val status = process?.waitFor()
            withContext(Dispatchers.Default) {
                val newText = buildAnnotatedString {
                    append(output.value.annotatedString)
                    append("\n\nProcess finished with exit code $status")
                }
                _output.value = output.value.copy(
                     annotatedString = newText,
                )
                _isRunning.value = false
            }
        }
    }

    fun endProcess() {
        process?.destroy()
    }

    fun userInput(text: TextFieldValue) {
        if (text.selection.end < lastValidCursorPosition && text.text.length < lastValidCursorPosition) return

        if (text.selection.end < lastValidCursorPosition && text.text.length != output.value.text.length) {
            if (text.text.length < output.value.text.length) {

                val newText = buildAnnotatedString { append(output.value.annotatedString.dropLast(1)) }
                _output.value = output.value.copy(
                    annotatedString = newText,
                    selection = TextRange(newText.length)
                )
            } else {

                val newText = buildAnnotatedString {
                    append(output.value.annotatedString)
                    withStyle(style = SpanStyle(color = primaryA0)) {
                        append(text.text.get(text.selection.end - 1))
                    }
                }
                _output.value = output.value.copy(
                    annotatedString = newText,
                    selection = TextRange(newText.length)
                )
            }
        } else {
            _output.value = output.value.copy(
                annotatedString = buildAnnotatedString {
                    if (output.value.text.length < text.text.length) {
                        append(output.value.annotatedString)
                        withStyle(style = SpanStyle(color = primaryA0)) {
                            append(text.text.drop(output.value.text.length))
                        }
                    } else if (output.value.text.length > text.text.length) {
                        append(output.value.annotatedString.dropLast(1))
                    } else {
                        append(output.value.annotatedString)
                    }
                },
                selection = text.selection
            )
        }
    }

    fun writeUserInput() {
        outputStream?.write(output.value.text.substring(lastValidCursorPosition, output.value.text.length).plus('\n'))
        outputStream?.flush()
        _output.value = output.value.copy(
            annotatedString = buildAnnotatedString {
                append(output.value.annotatedString)
                append('\n')
            },
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
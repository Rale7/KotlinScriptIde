package com.kotlin.script.ide.UI.editorpane

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.*
import com.kotlin.script.ide.data.FolderFile
import com.kotlin.script.ide.repositories.FolderRepository
import com.kotlin.script.ide.repositories.TabsRepository
import com.kotlin.script.ide.viewmodel.EditorPaneViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.compose.koinInject
import org.koin.core.component.inject
import test.BaseComposeTest
import kotlin.test.Test

class CodingTextAreaKtTest : BaseComposeTest() {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Before
    fun setUp() {

        val folderRepository: FolderRepository by inject()
        val tabsRepository: TabsRepository by inject()

        folderRepository.changeFolder(temporaryFolder.root.path)

        val file = temporaryFolder.newFile("test.kts")

        tabsRepository.addTabFile(FolderFile(name = file.name, directory = temporaryFolder.root.path))
    }

    private val textInput = "fun main() {\n\tprintln(\"Hello, world!\")\n}\n"

    @Test
    fun testTabulation() {

        super.setContent {
            val viewModel: EditorPaneViewModel = koinInject()
            val selectedFile by viewModel.selectedFile.collectAsState()


            if (selectedFile.fileName.isNotBlank()) {
                CodingTextArea(
                    selectedFile = selectedFile,
                    changeText = viewModel::changeText,
                    moveCursorDown = viewModel::moveCursorDown,
                    moveCursorUp = viewModel::moveCursorUp,
                    onTextAreaPressed = viewModel::onTextAreaPress,
                    focusRequester = viewModel.focusRequester,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("codingTextArea").performTextInput(textInput)
        composeTestRule.onNodeWithContentDescription("codingTextArea")
            .assertTextEquals(textInput.replace("\t", " ".repeat(4)))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testMovingCursor() {
        val viewModel : EditorPaneViewModel by inject()

        super.setContent {
            val selectedFile by viewModel.selectedFile.collectAsState()


            if (selectedFile.fileName.isNotBlank()) {
                CodingTextArea(
                    selectedFile = selectedFile,
                    changeText = viewModel::changeText,
                    moveCursorDown = viewModel::moveCursorDown,
                    moveCursorUp = viewModel::moveCursorUp,
                    onTextAreaPressed = viewModel::onTextAreaPress,
                    focusRequester = viewModel.focusRequester,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("codingTextArea").performClick()

        composeTestRule.onNodeWithContentDescription("codingTextArea").performKeyInput {
            keyDown(Key.Tab)
            keyUp(Key.Tab)
        }

        composeTestRule.onNodeWithContentDescription("codingTextArea").assertTextEquals(" ".repeat(4))
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testNumOfRow() {
        val numOfLines = 5
        val viewModel : EditorPaneViewModel by inject()

        super.setContent {
            val selectedFile by viewModel.selectedFile.collectAsState()


            if (selectedFile.fileName.isNotBlank()) {
                CodingTextArea(
                    selectedFile = selectedFile,
                    changeText = viewModel::changeText,
                    moveCursorDown = viewModel::moveCursorDown,
                    moveCursorUp = viewModel::moveCursorUp,
                    onTextAreaPressed = viewModel::onTextAreaPress,
                    focusRequester = viewModel.focusRequester,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        composeTestRule.onNodeWithContentDescription("codingTextArea").performClick()

        composeTestRule.onNodeWithContentDescription("codingTextArea").performKeyInput {
            repeat(numOfLines) {
                keyDown(Key.Enter)
                keyUp(Key.Enter)
            }
        }

        composeTestRule.onNodeWithContentDescription("numOfLines").onChildren().onLast().assertTextEquals((numOfLines + 1).toString())

        composeTestRule.onNodeWithContentDescription("codingTextArea").performKeyInput {
            keyDown(Key.Backspace)
            keyUp(Key.Backspace)
        }

        composeTestRule.onNodeWithContentDescription("numOfLines").onChildren().onLast().assertTextEquals((numOfLines).toString())
    }

}
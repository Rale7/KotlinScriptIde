package com.kotlin.script.ide.UI.outputpane

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.test.*
import com.kotlin.script.ide.repositories.FolderRepository
import com.kotlin.script.ide.repositories.TerminalRepository
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.core.component.inject
import test.BaseComposeTest
import kotlin.test.Test

class TerminalKtTest : BaseComposeTest() {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    private fun prepareInputScript(code: String) : String{
        val folderRepository: FolderRepository by inject()

        val file = temporaryFolder.newFile("test.kts")
        folderRepository.changeFolder(temporaryFolder.root.path)

        file.writeText(code)

        return file.name
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testTerminalInput() {
        val terminalRepository: TerminalRepository by inject()
        val code = """
            print("Enter input ")
            val input = readLine()?.let { it } ?: ""
        """

        super.setContent {
            Terminal()
        }

        val fileName = prepareInputScript(code.trimIndent())

        terminalRepository.runScript(temporaryFolder.root.path, fileName)

        val originalText = "Starting script $fileName...\n\n"

        composeTestRule.waitUntil(timeoutMillis = 20000) {
            terminalRepository.output.value.text != originalText
        }

        composeTestRule.onNodeWithContentDescription("terminal")
            .assertTextEquals("Starting script $fileName...\n\nEnter input ", includeEditableText = true)

        composeTestRule.onNodeWithContentDescription("terminal").performKeyInput {
            repeat(3) {
                keyDown(Key.Backspace)
                keyUp(Key.Backspace)
            }
        }

        composeTestRule.onNodeWithContentDescription("terminal")
            .assertTextEquals("Starting script $fileName...\n\nEnter input ", includeEditableText = true)

        composeTestRule.onNodeWithContentDescription("terminal").performTextInput("abc")

        composeTestRule.onNodeWithContentDescription("terminal")
            .assertTextEquals("Starting script $fileName...\n\nEnter input abc")

        composeTestRule.onNodeWithContentDescription("terminal").performClick()
        composeTestRule.onNodeWithContentDescription("terminal").performKeyInput {
            repeat(5) {
                keyDown(Key.DirectionLeft)
                keyUp(Key.DirectionLeft)
            }
            keyDown(Key.Backspace)
            keyUp(Key.Backspace)
        }

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("terminal")
            .assertTextEquals("Starting script $fileName...\n\nEnter input ab")

        repeat(5) {
            composeTestRule.onNodeWithContentDescription("terminal").performKeyInput {
                keyDown(Key.DirectionLeft)
                keyUp(Key.DirectionLeft)
            }
        }
        composeTestRule.onNodeWithContentDescription("terminal").performTextInput("s")

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("terminal")
            .assertTextEquals("Starting script $fileName...\n\nEnter input abs")

        composeTestRule.onNodeWithContentDescription("terminal").performKeyInput {
            keyDown(Key.DirectionLeft)
            keyUp(Key.DirectionLeft)
        }
        composeTestRule.onNodeWithContentDescription("terminal").performTextInput("z")

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("terminal")
            .assertTextEquals("Starting script $fileName...\n\nEnter input abzs")


    }


}
package com.kotlin.script.ide.UI.filelist

import androidx.compose.ui.test.*
import com.kotlin.script.ide.repositories.FolderRepository
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.core.component.inject
import test.BaseComposeTest
import kotlin.test.Test

class FIleListKtTest : BaseComposeTest() {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Before
    fun setUp() {
        val folderRepository : FolderRepository by inject()
        val testFolder = temporaryFolder.newFolder("test")
        folderRepository.changeFolder(testFolder.path)
    }

    @Test
    fun creatingNewFile() {
        super.setContent {
            FileList()
        }

        composeTestRule.onNodeWithContentDescription("Add file").performClick()
        composeTestRule.onNodeWithContentDescription("new_file_input_text").assertExists()
        composeTestRule.onNodeWithContentDescription("new_file_input_text").performTextInput("test")
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithContentDescription("new_file_input_text").assertDoesNotExist()
        composeTestRule.onNodeWithText("test.kts").assertExists()
    }

    @Test
    fun createAlreadyExistingFile() {
        super.setContent {
            FileList()
        }

        composeTestRule.onNodeWithContentDescription("Add file").performClick()
        composeTestRule.onNodeWithContentDescription("new_file_input_text").performTextInput("test")
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("test.kts").assertExists()
        composeTestRule.onNodeWithContentDescription("Add file").performClick()
        composeTestRule.onNodeWithContentDescription("new_file_input_text").performTextInput("test")
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("File already exists")
    }

    @Test
    fun createEmptyFile() {
        setContent { FileList() }

        composeTestRule.onNodeWithContentDescription("Add file").performClick()
        composeTestRule.onNodeWithText("Save").performClick()
        composeTestRule.onNodeWithText("File name cannot be empty").assertExists()
    }

    @Test
    fun createFileNameTooLong() {
        setContent { FileList() }

        composeTestRule.onNodeWithContentDescription("Add file").performClick()
        composeTestRule.onNodeWithContentDescription("new_file_input_text").performTextInput("t".repeat(500))
        composeTestRule.onNodeWithText("File name too long").assertExists()
        composeTestRule.onNodeWithText("t".repeat(50))
    }

}
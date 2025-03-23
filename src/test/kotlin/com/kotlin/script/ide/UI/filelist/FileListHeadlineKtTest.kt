package com.kotlin.script.ide.UI.filelist

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.kotlin.script.ide.repositories.FolderRepository
import com.kotlin.script.ide.viewmodel.FileListViewModel
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.core.component.inject
import test.BaseComposeTest
import kotlin.test.Test

class FileListHeadlineKtTest : BaseComposeTest() {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    private fun openFolder(folderName: String) {
        val folderRepository : FolderRepository by inject()
        val fileListViewModel: FileListViewModel by inject()

        super.setContent {
            val path by fileListViewModel.path.collectAsState()

            FileListHeadline(
                folderPath = getLastFolderName(path.path),
                onAddFile = {}
            )
        }

        val testFolder = temporaryFolder.newFolder(folderName)

        folderRepository.changeFolder(testFolder.path)
        composeTestRule.onNodeWithText(folderName).assertExists()
        composeTestRule.onNodeWithContentDescription("Add file").assertExists()
    }

    @Test
    fun testOpenedFolder() {
        openFolder("test")
    }

    @Test
    fun testOpenedFolderSpaces() {
        openFolder("New Folder")
    }

    @Test
    fun testOpenFolderLongName() {
        openFolder("rt".repeat(40))
    }

}
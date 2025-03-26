package com.kotlin.script.ide.repositories

import com.kotlin.script.ide.UI.repositories.TestConfigRepository
import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.koin.core.component.inject
import test.BaseComposeTest
import kotlin.test.Test


class FolderRepositoryTest : BaseComposeTest() {

    @get:Rule
    val temporaryFolder = TemporaryFolder()

    @Test
    fun testChangeFolder() {
        val folderRepository = FolderRepository(TestConfigRepository())

        temporaryFolder.newFile("main.kts")
        temporaryFolder.newFile("test.kts")

        folderRepository.changeFolder(temporaryFolder.root.path)

        Thread.sleep(1000)

        assert(folderRepository.files.value.any { it.name == "main.kts" })
        assert(folderRepository.files.value.any { it.name == "test.kts" })

    }

    @Test
    fun testDeleteFile() {
        val folderRepository: FolderRepository by inject()

        val fileMain = temporaryFolder.newFile("main.kts")
        temporaryFolder.newFile("test.kts")

        folderRepository.changeFolder(temporaryFolder.root.path)

        Thread.sleep(1000)

        fileMain.delete()

        Thread.sleep(1000)

        assert(folderRepository.files.value.any { it.name == "test.kts" })
        assertFalse(folderRepository.files.value.any { it.name == "main.kts" })
    }

    @Test
    fun testAddFile() {
        val folderRepository: FolderRepository by inject()

        temporaryFolder.newFile("main.kts")
        temporaryFolder.newFile("test.kts")

        folderRepository.changeFolder(temporaryFolder.root.path)

        Thread.sleep(1000)

        assertFalse(folderRepository.files.value.any { it.name == "test2.kts" })

        temporaryFolder.newFile("test2.kts")

        Thread.sleep(1000)

        assert(folderRepository.files.value.any { it.name == "test2.kts" })
    }

    @Test
    fun testNotDirectoryPassed() {
        val folderRepository: FolderRepository by inject()

        val file = temporaryFolder.newFile()

        folderRepository.changeFolder(file.path)

        Thread.sleep(1000)

        assert(folderRepository.folder.value.path != file.path)
    }

}
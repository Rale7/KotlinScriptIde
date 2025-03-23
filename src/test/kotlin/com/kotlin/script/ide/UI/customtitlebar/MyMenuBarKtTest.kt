package com.kotlin.script.ide.UI.customtitlebar

import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.window.WindowState
import test.BaseComposeTest
import kotlin.test.Test

class MyMenuBarKtTest : BaseComposeTest() {

    @Test
    fun testOpenFolder() {
        val windowState = WindowState()

        super.setContent {
            CustomTitleBar(
                windowState = windowState,
                onExit = {},
            )
        }

        composeTestRule.onNodeWithText("File").performClick()

        composeTestRule.onNodeWithText("Open Folder").assertExists()

    }

}
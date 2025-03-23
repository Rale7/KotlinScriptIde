package com.kotlin.script.ide.UI.customtitlebar

import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import org.junit.Assert.assertEquals
import test.BaseComposeTest
import java.awt.GraphicsEnvironment
import kotlin.test.Test


class CustomTitleControlsKtTest : BaseComposeTest() {
    private val windowBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().maximumWindowBounds

    @Test
    fun maximizeWindowTest() {
        val windowState = WindowState()

        super.setContent {
            CustomTitleBar(
                windowState = windowState,
                onExit = {},
            )
        }

        val oldSize = windowState.size
        val oldPosition = windowState.position

        composeTestRule.onNodeWithContentDescription("Maximize").performClick()

        assertEquals(windowState.position.x.value, 0f)
        assertEquals(windowState.position.y.value, 0f)
        assertEquals(windowState.size.width, windowBounds.width.dp)
        assertEquals(windowState.size.height, windowBounds.height.dp)

        composeTestRule.onNodeWithContentDescription("Maximize").performClick()

        assertEquals(oldSize, windowState.size)
        assertEquals(oldPosition, windowState.position)
    }

    @Test
    fun minimizeWindowTest() {

        val windowState = WindowState()

        super.setContent {
            CustomTitleBar(
                windowState = windowState,
                onExit = {},
            )
        }

        composeTestRule.onNodeWithContentDescription("Minimize").performClick()

        assertEquals(windowState.isMinimized, true)

    }
}
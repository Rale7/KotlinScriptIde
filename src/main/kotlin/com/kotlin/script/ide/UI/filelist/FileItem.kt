package com.kotlin.script.ide.UI.filelist

import com.kotlin.script.ide.Theme.primaryA2
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FileItem(
    name: String,
    onClick: () -> Unit,
    onDoubleClick: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
            .padding(start = 20.dp, top = 2.dp)
            .height(20.dp)
            .doubleClickable(
                onClick = onClick,
                onDoubleClick = onDoubleClick,
            )
    ) {
        Icon(
            imageVector = SimpleIcons.Kotlin,
            contentDescription = "logo",
            tint = primaryA2,
            modifier = Modifier.size(15.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = name,
            color = Color.White,
        )
    }

}

@Composable
fun Modifier.doubleClickable(
    onClick: () -> Unit,
    onDoubleClick: () -> Unit,
): Modifier {

    var lastClickTime by remember { mutableStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()

    return this.clickable {
        val currentTime = System.currentTimeMillis()
        val clickInterval = currentTime - lastClickTime

        if (clickInterval < 300) { // Double-click threshold (300ms)
            onDoubleClick()
            lastClickTime = 0 // Reset after double-click
        } else {
            coroutineScope.launch {
                lastClickTime = currentTime
                delay(300) // Wait for potential double-click
                if (lastClickTime != 0L) {
                    onClick() // Single-click action
                    lastClickTime = 0 // Reset after single-click
                }
            }
        }
    }

}
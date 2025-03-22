package com.kotlin.script.ide.UI.customtitlebar

import com.kotlin.script.ide.Theme.surfaceA0
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Kotlin
import com.kotlin.script.ide.viewmodel.TitleBarViewModel

@Composable
fun MyMenuBar(
    onOpenFolder: () -> Unit,
    viewModel: TitleBarViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier,
    ) {
        Icon(
            imageVector = SimpleIcons.Kotlin,
            contentDescription = null,
            modifier = Modifier.padding(8.dp),
            tint = Color.White
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = "File",
                modifier = Modifier.clickable(onClick = viewModel::showFileMenu),
                color = Color.White,
            )

            if (state.fileExpanded) {
                Popup(
                    onDismissRequest = viewModel::hideFileMenu,
                ) {
                    Column(
                        modifier = Modifier.offset(y = 22.dp, x = -5.dp)
                    ) {
                        Button(
                            onClick = onOpenFolder,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = surfaceA0,
                            ),
                            shape = RectangleShape,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
                            contentPadding = PaddingValues(start = 4.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                        ) {
                            Text(
                                text = "Open Folder",
                                color = Color.White,
                                fontSize = 12.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }

}
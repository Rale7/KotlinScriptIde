package UI.customtitlebar

import Theme.surfaceA0
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape

@Composable
fun MyMenuBar(
    onOpenFolder: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource("images/kotlin_logo.png"),
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "File",
            modifier = Modifier.clickable {
                expanded = true
            },
            color = Color.White,
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(
                color = surfaceA0,
                shape = RectangleShape
            )
        ) {
            DropdownMenuItem(
                onClick = onOpenFolder,
            ) {
                Text(
                    text = "Open folder",
                    color = Color.White,
                )
            }
        }
    }

}
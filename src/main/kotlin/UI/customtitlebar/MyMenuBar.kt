package UI.customtitlebar

import Theme.primaryA0
import Theme.surfaceA0
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.sp
import viewmodel.TitleBarViewModel

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
        Image(
            painter = painterResource("images/kotlin_logo.png"),
            contentDescription = null,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Box {
            Text(
                text = "File",
                modifier = Modifier.clickable(onClick = viewModel::showFileMenu),
                color = Color.White,
            )

            DropdownMenu(
                expanded = state.fileExpanded,
                onDismissRequest = viewModel::hideFileMenu,
                modifier = Modifier.background(
                    color = surfaceA0,
                    shape = RoundedCornerShape(0.dp)
                ).padding(0.dp),

                offset = DpOffset(x = 0.dp, y = -50.dp)
            ) {
                DropdownMenuItem(
                    onClick = onOpenFolder,
                    modifier = Modifier.background(
                        color = surfaceA0,
                        shape = RectangleShape
                    ),
                ) {
                    Text(
                        text = "Open folder",
                        color = Color.White,
                    )
                }
            }
        }
    }

}
package UI.editorpane

import Theme.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isTertiaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Kotlin
import data.FolderFile
import data.SelectedFile

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditorTabs(
    tabs: List<FolderFile>,
    selectedTab: SelectedFile,
    onClickTab: (selectedFile: FolderFile) -> Unit,
    onCloseTab: (FolderFile) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {

        items(tabs) {

            Row(
                modifier = Modifier.fillMaxSize()
                    .clickable { onClickTab(it) }
                    .drawBehind {
                        if (selectedTab.fileName == it.name) {
                            drawLine(
                                primaryA0,
                                Offset(0f, size.height),
                                Offset(size.width, size.height),
                                2f
                            )
                        }
                    }.padding(horizontal = 16.dp)
                    .onPointerEvent(PointerEventType.Scroll) { event ->
                        if (event.buttons.isTertiaryPressed) {
                            onCloseTab(it)
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Icon(
                    imageVector = SimpleIcons.Kotlin,
                    contentDescription = "icon",
                    tint = Color.White,
                    modifier = Modifier.size(15.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = it.name,
                    color = Color.White,
                )

                Spacer(modifier = Modifier.width(5.dp))


                Icon(
                    imageVector = ChromeClose,
                    contentDescription = "close",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                        .clickable { onCloseTab(it) }
                )

            }

        }

    }

}
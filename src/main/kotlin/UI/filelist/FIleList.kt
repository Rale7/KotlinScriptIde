package UI.filelist

import Theme.primaryA2
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import compose.icons.SimpleIcons
import compose.icons.simpleicons.Kotlin
import data.Folder
import org.koin.compose.koinInject
import viewmodel.FileListViewModel
import java.io.File

@Composable
fun FileList(
    modifier: Modifier = Modifier,
    viewModel: FileListViewModel = koinInject()
) {

    val folderPath by viewModel.path.collectAsState(initial = Folder())
    val files by viewModel.files.collectAsState(initial = listOf())

    NewFileDialog(
        onDismiss = { viewModel.hideDialog() },
        viewModel = viewModel,
    )

    Column(modifier = modifier) {
        FileListHeadline(
            folderPath = getLastFolderName(folderPath.path),
            onAddFile = { viewModel.showDialog() },
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 20.dp),
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(files) {

                FileItem(
                    it.name,
                    onClick = {},
                    onDoubleClick = { viewModel.addFileToTab(it) },
                )
            }
        }
    }
}

fun getLastFolderName(path: String) = if (path.isNotEmpty()) {
    File(path).nameWithoutExtension
} else {
    ""
}
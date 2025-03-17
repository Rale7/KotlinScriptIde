package UI.filelist

import Theme.tonalA1
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    Column(modifier = modifier) {
        FileListHeadline(
            folderPath = getLastFolderName(folderPath.path),
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 20.dp),
        )
    }
}

fun getLastFolderName(path: String) = if (path.isNotEmpty()) {
    File(path).nameWithoutExtension
} else {
    ""
}
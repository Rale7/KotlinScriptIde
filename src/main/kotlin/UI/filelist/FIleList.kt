package UI.filelist

import Theme.primaryA2
import Theme.tonalA1
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.sp
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

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .padding(start = 20.dp, top = 2.dp)
                        .height(20.dp)
                ) {
                    Icon(
                        imageVector = SimpleIcons.Kotlin,
                        contentDescription = "logo",
                        tint = primaryA2,
                        modifier = Modifier.size(15.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))

                    Text(
                        text = it.name,
                        color = Color.White,
                    )
                }
            }
        }
    }
}

fun getLastFolderName(path: String) = if (path.isNotEmpty()) {
    File(path).nameWithoutExtension
} else {
    ""
}
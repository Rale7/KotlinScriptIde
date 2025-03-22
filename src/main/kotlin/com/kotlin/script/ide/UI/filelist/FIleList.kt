package com.kotlin.script.ide.UI.filelist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kotlin.script.ide.data.Folder
import org.koin.compose.koinInject
import com.kotlin.script.ide.viewmodel.FileListViewModel
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
package UI.filelist

import Theme.tonalA1
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FileList(
    modifier: Modifier = Modifier,
) {

    Column(modifier = modifier) {
        FileListHeadline(
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(start = 20.dp),
        )
    }

}
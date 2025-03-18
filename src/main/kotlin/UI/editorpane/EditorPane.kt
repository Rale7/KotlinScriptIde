package UI.editorpane

import Theme.tonalA0
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import org.koin.compose.koinInject
import viewmodel.EditorPaneViewModel

@Composable
fun EditorPane(
    modifier: Modifier = Modifier,
    viewModel: EditorPaneViewModel = koinInject(),
) {
    val tabs by viewModel.tabs.collectAsState(initial = emptyList())
    val selectedFile by viewModel.selectedFile.collectAsState()

    Column(
        modifier = modifier
    ) {

        EditorTabs(
            tabs = tabs,
            selectedTab = selectedFile,
            onClickTab = viewModel::switchTab,
            onCloseTab = viewModel::closeTab,
            modifier = Modifier.fillMaxWidth()
                .height(40.dp)
                .background(
                    color = tonalA0,
                )
        )

        if (selectedFile.fileName.isNotBlank()) {
            CodingTextArea(
                selectedFile = selectedFile,
                changeText = viewModel::changeText,
                moveCursorDown = viewModel::moveCursorDown,
                moveCursorUp = viewModel::moveCursorUp,
                focusRequester = viewModel.focusRequester,
                modifier = Modifier.fillMaxWidth()
            )
        }



    }

}
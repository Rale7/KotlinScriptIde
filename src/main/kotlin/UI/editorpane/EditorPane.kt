package UI.editorpane

import Theme.tonalA0
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import data.SelectedFile
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
                modifier = Modifier.fillMaxWidth()
            )
        }



    }

}
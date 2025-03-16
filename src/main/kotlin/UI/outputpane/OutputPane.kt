package UI.outputpane

import Theme.tonalA0
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OutputPane(
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
    ) {
        OutputTabs(
            modifier = Modifier.fillMaxWidth()
                .height(30.dp)
                .background(color = tonalA0)
        )
    }

}
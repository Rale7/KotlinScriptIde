package UI.outputpane

import Theme.primaryA0
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OutputHeadline(
    modifier: Modifier = Modifier,
) {

    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier.padding(start = 10.dp),
    ) {
        Text(
            text = "Output",
            fontWeight = FontWeight.Bold,
            color = primaryA0,
            fontSize = 20.sp
        )
    }

}
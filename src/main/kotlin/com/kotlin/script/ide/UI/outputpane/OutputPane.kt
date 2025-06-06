package com.kotlin.script.ide.UI.outputpane

import com.kotlin.script.ide.Theme.tonalA0
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
        OutputHeadline(
            modifier = Modifier.fillMaxWidth()
                .height(30.dp)
                .background(color = tonalA0)
        )

        Terminal(
            modifier = Modifier.fillMaxSize()
        )
    }

}
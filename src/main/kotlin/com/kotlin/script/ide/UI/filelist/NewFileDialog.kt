package com.kotlin.script.ide.UI.filelist

import com.kotlin.script.ide.Theme.primaryA1
import com.kotlin.script.ide.Theme.surfaceA0
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kotlin.script.ide.viewmodel.FileListViewModel

@Composable
fun NewFileDialog(
    onDismiss: () -> Unit,
    viewModel: FileListViewModel
) {
    val state = viewModel.state.collectAsState()

    if (state.value.showDialog) {

        Dialog(
            onDismissRequest = onDismiss
        ) {
            Column(
                modifier = Modifier.background(surfaceA0)
                    .width(400.dp)
                    .height(300.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "New Kotlin File",
                    color = primaryA1,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = state.value.newFileName,
                    onValueChange = {viewModel.changeFileName(newFileName = it)},
                    label = { Text(state.value.newFileLabel) },
                    modifier = Modifier.fillMaxWidth().padding(20.dp)
                        .semantics { contentDescription = "new_file_input_text" },
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = primaryA1,
                        cursorColor = Color.White,
                        unfocusedBorderColor = primaryA1,
                        unfocusedLabelColor = primaryA1,
                        focusedLabelColor = primaryA1,
                        errorBorderColor = Color.Red,
                        errorLabelColor = Color.Red
                    ),
                    shape = RectangleShape,
                    isError = state.value.isError
                )

                Spacer(modifier = Modifier.height(40.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    DialogButton(
                        onClick = viewModel::createNewFile,
                        text = "Save",
                    )
                    DialogButton(
                        onClick = onDismiss,
                        text = "Cancel",
                    )
                }
            }
        }
    }
}

@Composable
fun DialogButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = primaryA1,
        ),
        shape = RectangleShape,
        elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
    ) {
        Text(text)
    }
}
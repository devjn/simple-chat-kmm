@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfayz.testchat.ui.preview

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jfayz.testchat.data.Provider
import com.jfayz.testchat.ui.components.AppBar
import com.jfayz.testchat.ui.components.EditTextDialog
import com.jfayz.testchat.ui.theme.AppTheme


@Preview
@Composable
private fun AppBarPreview() {
    AppTheme {
        AppBar(title = { Text("Preview!") })
    }
}

@Preview
@Composable
fun AppBarPreviewDark() {
    AppTheme(useDarkTheme = true) {
        AppBar(title = { Text("Preview!") })
    }
}

@Preview
@Composable
fun EditTextDialogPreview() {
    AppTheme {
        EditTextDialog(
            dialogTitle = "Dialog Title",
            hint = "Hint",
            onDismissRequest = { },
            onConfirmation = { },
            initial = Provider.apiKey
        )
    }
}

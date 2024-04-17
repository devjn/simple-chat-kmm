package com.jfayz.myapp.ui.chat.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ChatInputPreview() {
    ChatInput(onMessageSent = {})
}

@Composable
fun ChatInput(onMessageSent: (String) -> Unit, modifier: Modifier = Modifier, resetScroll: () -> Unit = {}) {
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    Surface(tonalElevation = 2.dp, contentColor = MaterialTheme.colorScheme.primary) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            UserInputTextField(
                textFieldValue = textState,
                onTextChanged = { textState = it },
                onTextFieldFocused = { focused ->
                    if (focused) {
                        resetScroll()
                    }
                },
                onImeSendAction = {
                    if (textState.text.isNotBlank()) {
                        onMessageSent(textState.text)
                        // Reset text field
                        textState = TextFieldValue()
                        // Move scroll to bottom
                        resetScroll()
                    }
                },
                modifier = Modifier.weight(1f)
            )
            // Send button
            FilledIconButton(
                enabled = textState.text.isNotBlank(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .testTag("SendButton"),
                onClick = {
                    onMessageSent(textState.text)
                    // Reset text field
                    textState = TextFieldValue()
                    // Move scroll to bottom
                    resetScroll()
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun RowScope.UserInputTextField(
    modifier: Modifier = Modifier,
    textFieldValue: TextFieldValue,
    onTextChanged: (TextFieldValue) -> Unit,
    onTextFieldFocused: (Boolean) -> Unit,
    onImeSendAction: () -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    var isFocused by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .padding(end = 8.dp)
            .align(Alignment.CenterVertically)
            .border(
                1.dp,
                if (isFocused) LocalContentColor.current else Color.Gray,
                RoundedCornerShape(16.dp)
            )
    ) {
        BasicTextField(
            value = textFieldValue,
            onValueChange = { onTextChanged(it) },
            modifier = modifier
                .semantics { contentDescription = "Chat input" }
                .padding(8.dp)
                .fillMaxWidth()
                .onFocusChanged { state ->
                    if (isFocused != state.isFocused) {
                        onTextFieldFocused(state.isFocused)
                    }
                    isFocused = state.isFocused
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Send
            ),
            keyboardActions = KeyboardActions(
                onSend = { onImeSendAction() }
            ),
            maxLines = 4,
            cursorBrush = SolidColor(LocalContentColor.current),
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.tertiary)
        )
    }
}

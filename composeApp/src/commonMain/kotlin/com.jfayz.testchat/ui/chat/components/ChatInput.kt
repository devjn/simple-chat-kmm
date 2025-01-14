package com.jfayz.testchat.ui.chat.components

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
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@Composable
fun ChatInput(onMessageSent: (String) -> Unit, modifier: Modifier = Modifier) {
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    // Fix for ImeAction.Send not clearing the text field on trigger.
    var keyboardSendTriggered by remember { mutableStateOf(false) }

    val onSendAction = {
        if (textState.text.isNotBlank()) {
            onMessageSent(textState.text)
            // Reset text field
            textState = TextFieldValue()
        }
    }

    Surface(tonalElevation = 2.dp, contentColor = MaterialTheme.colorScheme.primary) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            UserInputTextField(
                modifier = Modifier.weight(1f),
                textFieldValue = textState,
                onTextChanged = {
                    if (keyboardSendTriggered) {
                        // Reset text field
                        textState = TextFieldValue()
                        keyboardSendTriggered = false
                    } else {
                        textState = it
                    }
                },
                onSendAction = onSendAction,
                keyboardActions = KeyboardActions(onSend = {
                    keyboardSendTriggered = true
                    onSendAction()
                }),
            )
            // Send button
            FilledIconButton(
                enabled = textState.text.isNotBlank(),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .testTag("SendButton"),
                onClick = onSendAction
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
    onSendAction: () -> Unit,
    keyboardActions: KeyboardActions,
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
                .onFocusChanged { state -> isFocused = state.isFocused }
                .onKeyEvent { event ->
                    if (event.key == Key.Enter) {
                        onSendAction()
                        true
                    } else {
                        false
                    }
                },
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = ImeAction.Send
            ),
            keyboardActions = keyboardActions,
            maxLines = 4,
            cursorBrush = SolidColor(LocalContentColor.current),
            textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.tertiary)
        )
    }
}

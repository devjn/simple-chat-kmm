package com.jfayz.testchat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.data.Provider
import com.jfayz.testchat.ui.chat.ChatScreenContent
import com.jfayz.testchat.ui.chat.ChatViewModel
import com.jfayz.testchat.ui.components.EditTextDialog
import com.jfayz.testchat.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        AiChatScreen()
    }
}

@Composable
fun AiChatScreen(
    profile: Profile = Provider.getAiProfile(),
    viewModel: ChatViewModel = viewModel(
        ChatViewModel::class,
        key = Provider.apiKey,
        factory = getChatViewModelFactory(profile)
    )
) {
    val messages by viewModel.messages.collectAsState(emptyList())
    var showOptions by remember { mutableStateOf(false) }

    ChatScreenContent(
        profile,
        messages,
        onMessageSent = viewModel::sendMessage,
        onOptionsPressed = {
            showOptions = true
        }
    )

    if (showOptions) {
        ApiInputDialog(onDismiss = { showOptions = false })
    }
}

@Composable
fun ApiInputDialog(onDismiss: () -> Unit) {
    EditTextDialog(
        dialogTitle = "Enter API Key",
        hint = "API Key",
        initial = Provider.apiKey,
        onDismissRequest = onDismiss,
        onConfirmation = {
            Provider.apiKey = it
            onDismiss()
        }
    )
}

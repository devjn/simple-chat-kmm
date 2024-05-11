package com.jfayz.testchat.ui.chat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.getChatViewModelFactory
import com.jfayz.testchat.ui.chat.arch.ChatViewStore
import com.jfayz.testchat.ui.components.EditTextDialog
import kotlinproject.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun AiChatScreen(
    profile: Profile,
    store: ChatViewStore = viewModel(
        ChatViewStore::class,
        key = profile.uid.toString() + profile.apiKey.hashCode(),
        factory = getChatViewModelFactory(profile)
    ),
    onNavIconPressed: () -> Unit
) = with(store) {
    val uiState by state.collectAsState()
    val messages by messages.collectAsState(emptyList())

    ChatScreenContent(
        profile,
        messages,
        onMessageSent = { action(com.jfayz.testchat.ui.chat.arch.ChatActions.SendMessage(it)) },
        onNavIconPressed = onNavIconPressed,
        onOptionsPressed = { dispatch(com.jfayz.testchat.ui.chat.arch.ChatEvents.ShowOptions(true)) }
    )

    if (uiState.showOptions) {
        ApiInputDialog(profile, onDismiss = { dispatch(com.jfayz.testchat.ui.chat.arch.ChatEvents.ShowOptions(false)) })
    }
}

@Composable
fun ApiInputDialog(
    profile: Profile,
    onDismiss: () -> Unit
) {
    EditTextDialog(
        dialogTitle = stringResource(Res.string.api_key_title),
        hint = stringResource(Res.string.api_key),
        initial = profile.apiKey,
        onDismissRequest = onDismiss,
        onConfirmation = {
            profile.apiKey = it
            onDismiss()
        }
    )
}

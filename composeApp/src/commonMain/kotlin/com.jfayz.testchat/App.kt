package com.jfayz.testchat

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.ui.chat.ChatViewModel
import com.jfayz.testchat.ui.chat.ChatScreenContent
import com.jfayz.testchat.data.Provider

@Composable
fun App() = withViewModelStoreOwner {
    MaterialTheme {
        AiChatScreen()
    }
}

@Composable
fun AiChatScreen(
    profile: Profile = Provider.getAiProfile(),
    viewModel: ChatViewModel = viewModel(ChatViewModel::class, factory = getChatViewModelFactory(profile))
) {
    val messages by viewModel.messages.collectAsState(emptyList())

    ChatScreenContent(
        profile,
        messages,
        onMessageSent = viewModel::sendMessage,
    )
}

package com.jfayz.testchat

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jfayz.domain.model.Profile
import com.jfayz.myapp.ui.chat.ChatViewModel
import com.jfayz.testchat.ui.chat.ChatScreenContent
import com.jfayz.testchat.data.Provider

@Composable
fun App() {
    MaterialTheme {
        val profile = Provider.getAiProfile()
        AiChatScreen(profile)
    }
}

@Composable
fun AiChatScreen(profile: Profile) {
    val viewModel = ChatViewModel(Provider.chatRepo, profile)
    val messages by viewModel.messages.collectAsState(emptyList())

    ChatScreenContent(
        profile,
        messages,
        onMessageSent = { viewModel.sendMessage(it) },
    )
}

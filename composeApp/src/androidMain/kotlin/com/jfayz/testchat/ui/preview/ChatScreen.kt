@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfayz.testchat.ui.preview

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jfayz.chat.data.DummyData
import com.jfayz.testchat.ui.chat.components.ChatInput
import com.jfayz.testchat.ui.chat.ChatScreenContent
import com.jfayz.testchat.ui.theme.AppTheme
import kotlinx.datetime.Clock
import com.jfayz.testchat.ui.chat.components.*

@Preview
@Composable
fun ChatScreenPreview() {
    AppTheme {
        ChatScreenContent(
            profile = DummyData.friendProfile,
            messages = DummyData.initialMessages(),
            onMessageSent = { }
        )
    }
}

@Preview
@Composable
fun ChannelBarPrev() {
    AppTheme {
        ProfileNameBar(profile = DummyData.friendProfile)
    }
}

@Preview
@Composable
fun ChatInputPreview() {
    ChatInput(onMessageSent = {})
}

@Preview
@Composable
fun DayHeaderPrev() {
    DayHeader(Clock.System.now().epochSeconds)
}

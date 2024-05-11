package com.jfayz.testchat.ui.chat.arch

import androidx.compose.runtime.Immutable
import com.jfayz.chat.data.DummyData
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.arch.Action
import com.jfayz.testchat.arch.UiEvent
import com.jfayz.testchat.arch.UiState

@Immutable
data class ChatViewState(
    val myProfile: Profile = DummyData.myProfile,
    val showOptions: Boolean = false,
) : UiState

sealed interface ChatEvents : UiEvent {
    data class ShowOptions(val show: Boolean) : ChatEvents
}

sealed interface ChatActions : Action {
    data class SendMessage(val text: String) : ChatActions

}

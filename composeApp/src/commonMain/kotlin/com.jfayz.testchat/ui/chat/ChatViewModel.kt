package com.jfayz.myapp.ui.chat

import com.jfayz.chat.data.DummyData
import com.jfayz.domain.model.Profile
import com.jfayz.domain.repo.ChatRepository

class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val profile: Profile,
) /*: ViewModel()*/ {
    private val myProfile = DummyData.myProfile
    val messages = chatRepository.getMessages(profile.uid)

    fun sendMessage(text: String) /*= viewModelScope.launch(Dispatchers.IO)*/ {
        println("Sending message: $text")
        chatRepository.sendMessage(text, myProfile.uid, profile.uid)
    }
}

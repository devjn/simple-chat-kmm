package com.jfayz.testchat.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfayz.chat.data.DummyData
import com.jfayz.domain.model.Profile
import com.jfayz.domain.repo.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatRepository: ChatRepository,
    private val profile: Profile,
) : ViewModel() {
    private val myProfile = DummyData.myProfile
    val messages = chatRepository.getMessages(profile.uid)

    fun sendMessage(text: String) = viewModelScope.launch() {
        println("Sending message: $text")
        chatRepository.sendMessage(text, myProfile.uid, profile.uid)
    }
}

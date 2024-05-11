package com.jfayz.testchat

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.data.Provider
import com.jfayz.testchat.ui.chat.arch.ChatViewStore

fun getChatViewModelFactory(profile: Profile) = viewModelFactory {
    initializer { ChatViewStore(profile, Provider.chatRepo, Provider.getAiResponseUseCase(profile)) }
}

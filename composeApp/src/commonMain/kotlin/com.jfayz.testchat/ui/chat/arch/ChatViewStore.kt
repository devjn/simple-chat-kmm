package com.jfayz.testchat.ui.chat.arch

import com.jfayz.chat.data.DummyData
import com.jfayz.domain.model.Profile
import com.jfayz.domain.repo.ChatRepository
import com.jfayz.domain.usecase.GenerateAiResponseUseCase
import com.jfayz.testchat.arch.Actor
import com.jfayz.testchat.arch.Reducer
import com.jfayz.testchat.arch.Store

class ChatViewStore(
    profile: Profile,
    chatRepository: ChatRepository,
    generateAiResponseUseCase: GenerateAiResponseUseCase
) : Store<ChatViewState, ChatActions, ChatEvents>(
    initialState = ChatViewState(),
    reducer = chatViewReducer,
    actor = ChatViewActor(profile, DummyData.myProfile, chatRepository, generateAiResponseUseCase)
) {
    val messages = chatRepository.getMessages(profile.uid)
}

val chatViewReducer by lazy {
    Reducer<ChatViewState, ChatActions, ChatEvents> { event, state ->
        when (event) {
            is ChatEvents.ShowOptions -> state.copy(showOptions = event.show)
        }
    }
}

internal class ChatViewActor(
    private val profile: Profile,
    private val myProfile: Profile = DummyData.myProfile,
    private val chatRepository: ChatRepository,
    private val generateAiResponseUseCase: GenerateAiResponseUseCase
) : Actor<ChatActions, ChatEvents> {
    override suspend fun performAction(action: ChatActions): ChatEvents? = when (action) {
        is ChatActions.SendMessage -> with(action) {
            println("Sending message: $text")
            chatRepository.sendMessage(text, myProfile.uid, profile.uid)

            if (profile.isAi) {
                generateAiResponseUseCase(profile, myProfile, text)
            }
            null
        }
    }
}

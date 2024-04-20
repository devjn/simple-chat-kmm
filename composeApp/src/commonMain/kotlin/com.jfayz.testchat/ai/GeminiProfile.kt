package com.jfayz.testchat.ai

import androidx.compose.runtime.Immutable
import com.jfayz.chat.data.DummyData
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.Profile
import com.jfayz.domain.model.ProfileId
import com.jfayz.domain.repo.ChatRepository
import com.jfayz.testchat.data.Provider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Immutable
class GeminiProfile(
    name: String,
    private val chatRepo: ChatRepository,
    private val dataScope: CoroutineScope = Provider.dataScope,
    private val uiScope: CoroutineScope = Provider.sameScope
) : Profile(100L, name/*, "AI Profile"*/) {

    private val gemini = GeminiApi()

    init {
        uiScope.launch {
            chatRepo.getMessages(uid)
                .map { messages -> messages.first() }
                .filter { it.author != uid }
                .collectLatest {
                    println("GeminiProfile message: ${it.content}")
                    generateResponse(it)
                }
        }
    }

    private fun generateResponse(message: Message) = dataScope.launch {
        gemini.generateResponse(message.content)?.let {
            chatRepo.sendMessage(it, uid, DummyData.myProfile.uid)
        } ?: println("No response generated")
    }

}

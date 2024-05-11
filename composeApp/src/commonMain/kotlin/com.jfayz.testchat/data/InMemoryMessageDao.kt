package com.jfayz.testchat.data

import com.jfayz.chat.data.DummyData
import com.jfayz.domain.dao.MessageDao
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.Profile
import com.jfayz.domain.model.ProfileId
import com.jfayz.domain.model.isMine
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class InMemoryMessageDao : MessageDao {
    private var messages: MutableMap<ProfileId, List<Message>> = mutableMapOf()
    private val _messages = MutableSharedFlow<Map<ProfileId, List<Message>>>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        _messages.tryEmit(messages)
    }

    override fun insertMessage(message: Message) {
        val profile = message.nonMine()
        val currentMessages = messages.getOrPut(profile) { mutableListOf() }
        println("currentMessages: $currentMessages")
        messages[profile] = currentMessages.new(message)
        _messages.tryEmit(messages)
    }

    override fun getMessagesForProfile(profileId: ProfileId): Flow<List<Message>> {
        println("getMessagesForProfile: $profileId")
        return _messages.asSharedFlow().map { it[profileId] ?: emptyList() }
    }

}

private fun <T> Collection<T>.new(element: T): List<T> {
    val result = ArrayList<T>(size + 1)
    result.add(element)
    result.addAll(this)
    return result
}

private fun Message.nonMine(): ProfileId = if (isMine()) to else author

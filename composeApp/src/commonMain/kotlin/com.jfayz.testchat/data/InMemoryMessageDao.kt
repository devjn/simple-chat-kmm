package com.jfayz.testchat.data

import com.jfayz.chat.data.DummyData
import com.jfayz.domain.dao.MessageDao
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.ProfileId
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class InMemoryMessageDao : MessageDao {
    // Todo: Add separation by profiles
    private var messages : List<Message> = DummyData.initialMessages()
    private val _messages = MutableSharedFlow<List<Message>>(
        replay = 1,
        extraBufferCapacity = 0,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    init {
        _messages.tryEmit(messages)
    }

    override fun insertMessage(message: Message) {
        messages = messages.new(message)
        _messages.tryEmit(messages)
    }

    override fun getMessagesForProfile(profileId: ProfileId) = _messages.asSharedFlow()
}

private fun <T> Collection<T>.new(element: T): List<T> {
    val result = ArrayList<T>(size + 1)
    result.add(element)
    result.addAll(this)
    return result
}

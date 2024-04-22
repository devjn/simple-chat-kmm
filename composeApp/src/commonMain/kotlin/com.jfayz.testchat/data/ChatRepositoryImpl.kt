package com.jfayz.testchat.data

import com.jfayz.domain.dao.MessageDao
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.ProfileId
import com.jfayz.domain.repo.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.Clock

class ChatRepositoryImpl(
    private val messageDao: MessageDao,
    private val clock: Clock = Clock.System,
) : ChatRepository {

    override fun getMessages(profileId: ProfileId): Flow<List<Message>> =
        messageDao.getMessagesForProfile(profileId)

    override fun sendMessage(message: String, fromProfile: ProfileId, toProfile: ProfileId) {
        messageDao.insertMessage(createMessage(message, fromProfile, toProfile))
    }

    private fun createMessage(
        message: String,
        fromProfile: ProfileId,
        toProfile: ProfileId,
    ) = Message(
        message,
        clock.now(),
        fromProfile,
        toProfile,
    )
}

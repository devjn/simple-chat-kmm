package com.jfayz.domain.repo

import com.jfayz.domain.model.Message
import com.jfayz.domain.model.ProfileId
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    fun getMessages(profileId: ProfileId): Flow<List<Message>>
    fun sendMessage(message: String, fromProfile: ProfileId, toProfile: ProfileId)
}
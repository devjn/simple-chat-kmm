package com.jfayz.domain.dao

import com.jfayz.domain.model.Message
import com.jfayz.domain.model.ProfileId
import kotlinx.coroutines.flow.Flow

/**
 * On mobile, this would be implemented by a Room database.
 * On web, this would be stored in memory.
 */
interface MessageDao {
    fun insertMessage(message: Message)
    fun getMessagesForProfile(profileId: ProfileId): Flow<List<Message>>
}

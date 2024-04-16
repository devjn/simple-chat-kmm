package data

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.jfayz.chat.data.DummyData
import com.jfayz.domain.dao.MessageDao
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.ProfileId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow

class InMemoryMessageDao : MessageDao {
    // Todo: Add separation by profiles
    private val messages = DummyData.initialMessages()
    private val _messages = MutableStateFlow<SnapshotStateList<Message>>(messages)

    init {
        _messages.tryEmit(messages)
    }

    override fun insertMessage(message: Message) {
        messages.add(0, message)
        _messages.tryEmit(messages)
    }

    override fun getMessagesForProfile(profileId: ProfileId) = _messages.asSharedFlow()
}
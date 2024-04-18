package ui.chat.components

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import com.jfayz.chat.data.DummyData
import com.jfayz.domain.model.Message
import com.jfayz.testchat.ui.chat.ChatScreenContent
import com.jfayz.myapp.ui.chat.components.ChatScreenPreview
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalTestApi::class)
class ChatScreenTest {
    @Test
    fun verifyIfAllViewsIsDisplayed() = runComposeUiTest {
        setContent {
            ChatScreenPreview()
        }

        onNodeWithTag("SendButton").isDisplayed()
        chatInputNode.isDisplayed()
    }

    @Test
    fun verifySendTextIsDisplayed() = runComposeUiTest {
        val expectedMessage = "Hello"
        val sentMessages = mutableStateListOf<Message>()
        setContent {
            ChatScreenContent(
                profile = DummyData.friendProfile,
                messages = sentMessages,
                onMessageSent = { message ->
                    sentMessages.add(
                        Message(message, 0L)
                    )
                }
            )
        }

        chatInputNode.performTextInput(expectedMessage)
        onNodeWithTag("SendButton").performClick()

        assertTrue(sentMessages.size == 1)
        assertEquals(expectedMessage, sentMessages[0].content)
        onNode(hasText(expectedMessage)).isDisplayed()
    }

    private val SemanticsNodeInteractionsProvider.chatInputNode
        get() = onNodeWithContentDescription("Chat input")
}

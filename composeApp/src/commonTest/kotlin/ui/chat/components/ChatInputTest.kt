package ui.chat.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import com.jfayz.testchat.ui.chat.components.ChatInput
import kotlin.test.Test
import kotlin.test.assertEquals

class ChatInputTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun chatInputWorksCorrectly() = runComposeUiTest {
        val expectedMessage = "Hello"
        val sentMessages = mutableListOf<String>()
        val onMessageSend: (String) -> Unit = { sentMessage ->
            sentMessages.add(sentMessage)
        }
        setContent {
            ChatInput(
                onMessageSent = onMessageSend,
            )
        }

        // sendButtonNode should not be enabled until text is entered.
        sendButtonNode.assertIsNotEnabled()

        chatInputNode.performTextInput(expectedMessage)
        chatInputNode.assertTextEquals(expectedMessage)
        sendButtonNode.assertIsEnabled()

        sendButtonNode.performClick()
        chatInputNode.assertTextEquals("")
        sendButtonNode.assertIsNotEnabled()
        assertEquals(listOf(expectedMessage), sentMessages)
    }

    private val SemanticsNodeInteractionsProvider.chatInputNode
        get() = onNodeWithContentDescription("Chat input")

    private val SemanticsNodeInteractionsProvider.sendButtonNode
        get() = onNodeWithTag("SendButton")
}

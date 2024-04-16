import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.jfayz.domain.model.Profile
import com.jfayz.myapp.ui.chat.ChatViewModel
import com.jfayz.myapp.ui.chat.components.ChatScreenContent
import data.Provider
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val profile = Provider.getAiProfile()
        AiChatScreen(profile)
    }
}

@Composable
@Preview
fun AiChatScreen(profile: Profile) {
    val viewModel = ChatViewModel(Provider.chatRepo, profile)
    val messages by viewModel.messages.collectAsState(emptyList())

    ChatScreenContent(
        profile,
        messages,
        onMessageSent = { viewModel.sendMessage(it) },
    )
}

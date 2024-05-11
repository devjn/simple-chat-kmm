package com.jfayz.testchat

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.ui.chat.AiChatScreen
import com.jfayz.testchat.ui.profiles.ProfilesScreen
import com.jfayz.testchat.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        CurrentScreen()
    }
}

@Composable
fun CurrentScreen() {
    var currentScreen by remember { mutableStateOf(Screen.ProfilesScreen) }
    var clickedProfile: Profile? by remember { mutableStateOf(null) }

    // Todo: Implement proper navigation
    when (currentScreen) {
        Screen.ProfilesScreen -> ProfilesScreen() {
            println("Profile clicked: $it")
            clickedProfile = it
            currentScreen = Screen.ChatScreen
        }

        Screen.ChatScreen -> AiChatScreen(clickedProfile!!) {
            clickedProfile = null
            currentScreen = Screen.ProfilesScreen
        }
    }
}

enum class Screen {
    ChatScreen,
    ProfilesScreen
}

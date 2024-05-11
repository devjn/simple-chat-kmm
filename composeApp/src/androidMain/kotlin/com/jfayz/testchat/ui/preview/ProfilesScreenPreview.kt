package com.jfayz.testchat.ui.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.jfayz.chat.data.DummyData
import com.jfayz.testchat.ui.profiles.ProfilesScreen
import com.jfayz.testchat.ui.profiles.arch.ProfilesEvent
import com.jfayz.testchat.ui.profiles.arch.ProfilesStore
import com.jfayz.testchat.ui.profiles.components.AiProfileForm
import com.jfayz.testchat.ui.theme.AppTheme

@Preview
@Composable
fun ProfilesScreenPreview() = AppTheme {
    val store = ProfilesStore().also {
        it.dispatch(ProfilesEvent.ProfilesLoaded(listOf(DummyData.friendProfile, DummyData.myProfile)))
    }
    ProfilesScreen(
        store= store,
        onOptionsPressed = { },
    )
}

@Preview
@Composable
fun AiProfileFormPreview() {
    AppTheme {
        AiProfileForm(
            { },
            { _, _, _ -> }
        )
    }
}

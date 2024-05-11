package com.jfayz.testchat.ui.profiles.arch

import androidx.compose.runtime.Immutable
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.arch.Action
import com.jfayz.testchat.arch.SideEffect
import com.jfayz.testchat.arch.UiEvent
import com.jfayz.testchat.arch.UiState

@Immutable
data class ProfilesState(
    val profiles: List<Profile> = emptyList(),
    val showDialog: Boolean = false,
    val isLoading: Boolean = false,
    val error: String = "",
    val showSnackbar: SideEffect<Boolean> = SideEffect(false)
) : UiState

sealed interface ProfilesEvent : UiEvent {
    data class ProfileSelected(val profile: Profile) : ProfilesEvent
    data object Init : ProfilesEvent
    data class ShowDialog(val show: Boolean) : ProfilesEvent
    data class ProfilesLoaded(val profiles: List<Profile>) : ProfilesEvent
    data class Error(val error: String) : ProfilesEvent
}

sealed interface ProfilesAction : Action {
    data object LoadProfiles : ProfilesAction
    data class NewProfile(val name: String, val apiKey: String, val aiType: String) : ProfilesAction
}

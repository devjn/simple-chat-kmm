package com.jfayz.testchat.ui.profiles.arch

import com.jfayz.domain.dao.ProfileDao
import com.jfayz.domain.usecase.CreateAiProfileUseCase
import com.jfayz.testchat.arch.Actor
import com.jfayz.testchat.arch.Reducer
import com.jfayz.testchat.arch.Store
import com.jfayz.testchat.data.Provider

class ProfilesStore : Store<ProfilesState, ProfilesAction, ProfilesEvent>(
    actor = ContactsActor(),
    initialState = ProfilesState(),
    initEvent = ProfilesEvent.Init,
    reducer = contactsReducer()
)

fun contactsReducer() = Reducer<ProfilesState, ProfilesAction, ProfilesEvent> { event, state ->
    when (event) {
        is ProfilesEvent.Init -> state.also { action(ProfilesAction.LoadProfiles) }
        is ProfilesEvent.ProfilesLoaded -> state.copy(profiles = event.profiles, isLoading = false)
        is ProfilesEvent.Error -> state.copy(error = event.error, isLoading = false)
        is ProfilesEvent.ShowDialog -> state.copy(showDialog = event.show).also {
            if (!event.show) {
                it.showSnackbar(true)
                println("Show snackbar")
            }
        }

        is ProfilesEvent.ProfileSelected -> TODO()
    }
}

class ContactsActor(
    private val createAiProfileUseCase: CreateAiProfileUseCase = Provider.getCreateAiProfileUseCase(),
    private val profileDao: ProfileDao = Provider.profileDao
) : Actor<ProfilesAction, ProfilesEvent> {
    override suspend fun performAction(action: ProfilesAction): ProfilesEvent? = when (action) {
        is ProfilesAction.NewProfile -> {
            createAiProfileUseCase.invoke(action.name, action.apiKey, action.aiType)
            performAction(ProfilesAction.LoadProfiles)
        }

        ProfilesAction.LoadProfiles -> {
            profileDao.getProfiles().let {
                ProfilesEvent.ProfilesLoaded(it)
            }
        }
    }
}

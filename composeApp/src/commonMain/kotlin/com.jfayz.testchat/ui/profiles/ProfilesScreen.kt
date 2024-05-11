package com.jfayz.testchat.ui.profiles

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jfayz.domain.model.Profile
import com.jfayz.testchat.ui.profiles.arch.ProfilesAction
import com.jfayz.testchat.ui.profiles.arch.ProfilesEvent
import com.jfayz.testchat.ui.profiles.arch.ProfilesStore
import com.jfayz.testchat.ui.profiles.components.AiProfileForm
import com.jfayz.testchat.ui.profiles.components.ProfileCell
import kotlinx.coroutines.launch

@Composable
fun ProfilesScreen(
    store: ProfilesStore = ProfilesStore(),
    onOptionsPressed: (Profile) -> Unit,
) = with(store) {
    val state by state.collectAsState()
    val scrollState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            BottomAppBar(
                actions = { },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { dispatch(ProfilesEvent.ShowDialog(true)) },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add"
                            )
                        })
                }
            )
        },
    ) { paddingValues ->
        Column(
            Modifier.fillMaxSize().padding(paddingValues)
        ) {
            LazyColumn(
                state = scrollState, modifier = Modifier.fillMaxSize()
            ) {
                items(items = state.profiles, key = { item -> item.uid.uid }, itemContent = { profile ->
                    ProfileCell(
                        modifier = Modifier.clickable { onOptionsPressed(profile) }, profile = profile
                    )
                })
            }

            if (state.showDialog) {
                AiProfileForm(
                    onDismissRequest = { dispatch(ProfilesEvent.ShowDialog(false)) },
                    onConfirm = { name, apiKey, aiType ->
                        action(ProfilesAction.NewProfile(name, apiKey, aiType))
                        dispatch(ProfilesEvent.ShowDialog(false))
                    }
                )
            }
            if (state.showSnackbar.value) {
                scope.launch {
                    snackbarHostState.showSnackbar("New profile added")
                }
            }
        }
    }
}

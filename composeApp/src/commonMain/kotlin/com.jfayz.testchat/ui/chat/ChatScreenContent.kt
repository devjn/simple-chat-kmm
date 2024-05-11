@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfayz.testchat.ui.chat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.Profile
import com.jfayz.domain.model.isMine
import com.jfayz.testchat.ui.chat.components.ChatInput
import com.jfayz.testchat.ui.chat.components.ChatMessage
import com.jfayz.testchat.ui.chat.components.DayHeader
import com.jfayz.testchat.ui.chat.components.JumpToBottom
import com.jfayz.testchat.ui.chat.components.ProfileNameBar
import com.jfayz.testchat.ui.chat.components.shouldAddHeader
import kotlinx.coroutines.launch

/**
 * Entry point for a chat screen.
 *
 * @param profile [Profile] profile to display
 * @param messages [List] of [Message] to display
 * @param onMessageSent [Function] to call when a message is sent
 * @param onNavIconPressed Sends an event up when the user clicks on the menu
 * @param onOptionsPressed Sends an event up when the user clicks on the options
 */
@Composable
fun ChatScreenContent(
    profile: Profile,
    messages: List<Message>,
    onMessageSent: (String) -> Unit,
    modifier: Modifier = Modifier,
    onNavIconPressed: () -> Unit = { },
    onOptionsPressed: () -> Unit = { }
) {
    val scrollState = rememberLazyListState()
    val topBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(topBarState)

    LaunchedEffect(messages) {
        val lastMessage = messages.firstOrNull() ?: return@LaunchedEffect
        scrollToLatestMessage(scrollState, lastMessage)
    }

    Scaffold(
        topBar = {
            ProfileNameBar(
                profile = profile,
                scrollBehavior = scrollBehavior,
                onNavIconPressed = onNavIconPressed,
                onOptionsPressed = onOptionsPressed
            )
        },

        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets
            .exclude(WindowInsets.navigationBars)
            .exclude(WindowInsets.ime),
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Messages(
                messages = messages,
                modifier = Modifier.weight(1f),
                scrollState = scrollState
            )
            ChatInput(
                onMessageSent = onMessageSent,
                // let this element handle the padding so that the elevation is shown behind the navigation bar
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Messages(messages: List<Message>, scrollState: LazyListState, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    Box(modifier = modifier) {
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(
                items = messages,
                key = { index, message -> message.timestamp },
                { index, item -> null }) { index, message ->
                // TODO: Use Modifier.animateItem() from compose 1.7.0
                ChatMessage(Modifier.animateItemPlacement(), message)

                val shouldDisplayHeader = remember(message, index) { shouldAddHeader(message, index, messages) }
                if (shouldDisplayHeader) {
                    DayHeader(message.timestamp)
                }
            }
        }

        // Show the button when the user scrolls up
        val jumpToBottomButtonEnabled by remember {
            derivedStateOf {
                scrollState.firstVisibleItemIndex > 1
            }
        }

        JumpToBottom(
            visible = jumpToBottomButtonEnabled,
            onClicked = {
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 16.dp)
        )
    }
}

suspend fun scrollToLatestMessage(scrollState: LazyListState, lastMessage: Message) {
    // If the new message is mine, scroll to it, otherwise animate
    val isNewMessageMine = lastMessage.isMine()
    if (isNewMessageMine) {
        scrollState.scrollToItem(0)
    } else {
        scrollState.animateScrollToItem(0)
    }
}

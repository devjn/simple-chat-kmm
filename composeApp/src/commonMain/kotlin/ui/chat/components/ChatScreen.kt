@file:OptIn(ExperimentalMaterial3Api::class)

package com.jfayz.myapp.ui.chat.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.jfayz.chat.data.DummyData
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.Profile
import com.jfayz.domain.model.isMine
import com.jfayz.myapp.ui.base.components.AppBar
import com.jfayz.myapp.ui.theme.AppTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.chat.components.DayHeader
import ui.chat.components.shouldAddHeader

/**
 * Entry point for a chat screen.
 *
 * @param profile [Profile] profile to display
 * @param messages [List] of [Message] to display
 * @param onMessageSent [Function] to call when a message is sent
 * @param onNavIconPressed Sends an event up when the user clicks on the menu
 * @param onOptionsPressed Sends an event up when the user clicks on the options
 */
@OptIn(ExperimentalMaterial3Api::class)
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
    val scope = rememberCoroutineScope()

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
                resetScroll = {
                    scope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                // let this element handle the padding so that the elevation is shown behind the navigation bar
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileNameBar(
    profile: Profile,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onNavIconPressed: () -> Unit = { },
    onOptionsPressed: () -> Unit = { }
) {
    AppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        onNavIconPressed = onNavIconPressed,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(8.dp),
                    contentDescription = "Profile Picture"
                )
                Text(
                    text = profile.name,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        actions = {
            Icon(
                imageVector = Icons.Outlined.MoreHoriz,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .clickable(onClick = onOptionsPressed)
                    .padding(8.dp),
                contentDescription = "More"
            )
        }
    )
}

@Composable
fun Messages(messages: List<Message>, scrollState: LazyListState, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        LazyColumn(
            reverseLayout = true,
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            for (index in messages.indices) {
                val message = messages[index]

                // Use timestamp as key for now. Changed to ID later
                item(key = message.timestamp) {
                    ChatMessage(message)
                }

                if (shouldAddHeader(message, index, messages)) {
                    item(key = "header${message.timestamp}") {
                        DayHeader(message.timestamp)
                    }
                }
            }
        }
    }
}

@Composable
fun ChatMessage(msg: Message) {
    val isUserMe = msg.isMine()
    val modifier = if (isUserMe) {
        Modifier.padding(start = 72.dp)
    } else {
        Modifier.padding(end = 72.dp)
    }
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                bottom = 8.dp, // Space between bubbles
                start = if (isUserMe) 0.dp else 16.dp,
                end = if (isUserMe) 16.dp else 0.dp
            ),
        contentAlignment = if (isUserMe) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        ChatItemBubble(
            message = msg,
            isUserMe = isUserMe
        )
    }
}

@Composable
fun ChatItemBubble(message: Message, isUserMe: Boolean) {
    val backgroundBubbleColor = if (isUserMe) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    Surface(
        color = backgroundBubbleColor,
        shape = if (isUserMe) AuthorChatBubbleShape else ChatBubbleShape
    ) {
        Box {
            Text(
                text = message.content,
                style = MaterialTheme.typography.bodyLarge.copy(color = LocalContentColor.current),
                modifier = Modifier.padding(8.dp)
            )
            if (isUserMe) {
                Icon(
                    imageVector = Icons.Default.DoneAll,
                    contentDescription = "Message sent",
                    tint = Color.LightGray,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(2.dp)
                        .size(10.dp)
                )
            }
        }
    }
}

private val ChatBubbleShape = RoundedCornerShape(20.dp, 20.dp, 20.dp, 4.dp)
private val AuthorChatBubbleShape = RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp)

@Preview
@Composable
fun ChatScreenPreview() {
    AppTheme {
        ChatScreenContent(
            profile = DummyData.friendProfile,
            messages = DummyData.initialMessages(),
            onMessageSent = { }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ChannelBarPrev() {
    AppTheme {
        ProfileNameBar(profile = DummyData.friendProfile)
    }
}

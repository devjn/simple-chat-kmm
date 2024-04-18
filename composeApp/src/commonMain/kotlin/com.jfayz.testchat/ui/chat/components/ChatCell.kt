package com.jfayz.testchat.ui.chat.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.isMine


@Composable
fun ChatMessage(modifier: Modifier, msg: Message) {
    val isUserMe = msg.isMine()
    val boxModifier = if (isUserMe) {
        modifier.padding(start = 72.dp)
    } else {
        modifier.padding(end = 72.dp)
    }
    Box(
        modifier = boxModifier
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

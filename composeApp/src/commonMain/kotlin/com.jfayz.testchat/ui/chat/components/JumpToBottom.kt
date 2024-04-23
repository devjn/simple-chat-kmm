package com.jfayz.testchat.ui.chat.components

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Shows a button that lets the user scroll to the bottom.
 */
@Composable
fun JumpToBottom(
    visible: Boolean,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val transition = updateTransition(
        visible,
        label = "JumpToBottom visibility animation"
    )
    val bottomOffset by transition.animateDp(label = "JumpToBottom offset animation") {
        if (visible) {
            32.dp
        } else {
            (-32).dp
        }
    }
    if (bottomOffset > 0.dp) {
        FloatingActionButton(
            onClick = onClicked,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = modifier.offset(x = 0.dp, y = -bottomOffset)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDownward,
                contentDescription = null
            )
        }
    }
}

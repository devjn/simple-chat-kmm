package com.jfayz.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Message(
    val content: String,
    val timestamp: Long,
    val author: ProfileId,
    val to: ProfileId,
)

fun Message.isMine(): Boolean {
    // Dummy check by id.
    return 1L == author.uid
}

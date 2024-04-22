package com.jfayz.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.datetime.Instant

@Immutable
data class Message(
    val content: String,
    val timestamp: Long,
    val author: ProfileId,
    val to: ProfileId,
) {
    constructor(
        content: String,
        time: Instant,
        author: ProfileId,
        to: ProfileId,
    ) : this(content, time.toEpochMilliseconds(), author, to)
}

fun Message.isMine(): Boolean {
    // Dummy check by id.
    return 1L == author.uid
}

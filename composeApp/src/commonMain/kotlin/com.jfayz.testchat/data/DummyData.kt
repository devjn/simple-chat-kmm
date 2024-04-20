package com.jfayz.chat.data

import androidx.compose.runtime.mutableStateListOf
import com.jfayz.domain.model.Message
import com.jfayz.domain.model.Profile
import com.jfayz.domain.model.ProfileId
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

/**
 * Dummy data for tests.
 */
object DummyData {
    val myProfile = Profile(1, "Me")
    val friendProfile = Profile(2, "Sarah")

    private val date = Clock.System.now()

    fun initialMessages(
        me: ProfileId = myProfile.uid,
        friend: ProfileId = ProfileId(100L),
    ) = mutableStateListOf(
        Message("Hello!", date.minus(1.days).toEpochMilliseconds(), me, friend),
        Message("Hi!", date.minus(4.hours).toEpochMilliseconds(), friend, me),
        Message(
            "How are you?",
            date.minus(100.minutes).toEpochMilliseconds(),
            me,
            friend,
        ),
        Message(
            "I'm fine, thanks!",
            date.minus(90.minutes).toEpochMilliseconds(),
            friend,
            me,
        ),
        Message(
            "What are you doing?",
            date.minus(80.minutes).toEpochMilliseconds(),
            me,
            friend,
        ),
        Message(
            "Nothing much, just chilling",
            date.minus(70.minutes).toEpochMilliseconds(),
            friend,
            me,
        ),
    ).also {
        // Use sort instead of sorted as it changes list typeS
        it.sortByDescending { it.timestamp }
    }

    val responses =
        listOf(
            "I'm busy right now, can I get back to you later?",
            "I'm not sure, let me check and get back to you.",
            "I'm sorry, I don't have an answer for that right now.",
        )
}

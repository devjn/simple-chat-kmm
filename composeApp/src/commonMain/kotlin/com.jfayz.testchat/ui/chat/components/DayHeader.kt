package com.jfayz.testchat.ui.chat.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jfayz.domain.model.Message
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Duration.Companion.hours

@Composable
fun DayHeader(timestamp: Long) {
    val time = remember { getTimeSectionText(timestamp) }
    Text(
        text = time,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.77f)
    )
}

private val oneHourInMillis = 1.hours.inWholeMilliseconds

// Helper function to determine if a header should be added
fun shouldAddHeader(message: Message, index: Int, messages: List<Message>): Boolean {
    if (index == 0) {
        return false
    }
    // Check if the current message is the oldest one
    // or if it was sent more than an hour after the previous one
    val isLast = index == messages.size - 1
    return isLast || isMoreThanAnHourApart(message, messages[index + 1])
}

private fun isMoreThanAnHourApart(currentMessage: Message, prevMessage: Message): Boolean {
    val difference = currentMessage.timestamp - prevMessage.timestamp
    return difference > oneHourInMillis
}

private const val formatPattern = "dd-MM HH:mm"

@OptIn(FormatStringsInDatetimeFormats::class)
val dateTimeFormat = LocalDateTime.Format {
    byUnicodePattern(formatPattern)
}

// TODO: Separate days
private fun getTimeSectionText(timestamp: Long): String {
    val instant = Instant.fromEpochMilliseconds(timestamp)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return dateTimeFormat.format(localDateTime)
}

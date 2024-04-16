package com.jfayz.domain.model

import kotlin.test.Test
import kotlin.test.assertEquals


class ModelTests {

    @Test
    fun testMessageCreation() {
        val author = Profile(1L, "Author", "Bio")
        val recipient = Profile(2L, "Recipient", "Bio")
        val message = Message("Test message", 1633024800L, author.uid, recipient.uid)

        assertEquals("Test message", message.content)
        assertEquals(1633024800L, message.timestamp)
        assertEquals(author.uid, message.author)
        assertEquals(recipient.uid, message.to)
    }

    @Test
    fun testProfileCreation() {
        val profile = Profile(1L, "Test User", "Bio")

        assertEquals(ProfileId(1L), profile.uid)
        assertEquals("Test User", profile.name)
        assertEquals("Bio", profile.bio)
    }
}



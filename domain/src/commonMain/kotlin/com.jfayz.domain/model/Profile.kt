package com.jfayz.domain.model

import kotlin.jvm.JvmInline

open class Profile(
    val uid: ProfileId,
    val name: String,
    val bio: String = ""
) {
    constructor(uid: Long, name: String) : this(ProfileId(uid), name)
}

@JvmInline
/**
 * Unique identifier for a profile. Should be obtained from the server.
 */
value class ProfileId(val uid: Long) {
    init {
        require(uid >= 0) { "ProfileId must be positive" }
    }
}

fun Long.toProfileId(): ProfileId = ProfileId(this)

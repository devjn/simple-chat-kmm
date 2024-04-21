package com.jfayz.domain.model

import kotlin.jvm.JvmInline

data class Profile(
    val uid: ProfileId,
    val name: String,
    val type: ProfileType,
    val bio: String = "",
) {
    val isAi: Boolean
        get() = type == ProfileType.AI

    constructor(uid: Long, name: String) : this(ProfileId(uid), name, ProfileType.PERSON)

    companion object {
        fun ai(uid: ProfileId, name: String) = Profile(uid, name, ProfileType.AI, "AI Profile")
    }
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

enum class ProfileType(val value: Int) {
    PERSON(1),
    GROUP(2),
    AI(3),
}

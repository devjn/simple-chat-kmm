package com.jfayz.domain.model

import kotlin.jvm.JvmInline

open class Profile()  { // Empty constructor required by Realm
    var uid: ProfileId = ProfileId(0) // Should come from server
    var name: String = ""
    var bio: String = ""

    /**
     * Constructor for creating a dummy Profile object
     */
    constructor(uid: Long, name: String, bio: String) : this() {
        this.uid = uid.toProfileId()
        this.name = name
        this.bio = bio
    }
}

@JvmInline
value class ProfileId(val uid: Long) {
    init {
        require(uid >= 0) { "ProfileId must be positive" }
    }
}

fun Long.toProfileId(): ProfileId = ProfileId(this)

package com.jfayz.domain.model

/*Empty constructor required by Realm*/
class Message() {
    var content: String = ""
    var timestamp: Long = 0

    /** UID for author */
    var author: ProfileId = ProfileId(0)

    /** UID for recipient */
    var to: ProfileId = ProfileId(0)

    constructor(content: String, timestamp: Long, authorId: ProfileId, toId: ProfileId) : this() {
        this.content = content
        this.timestamp = timestamp
        this.author = authorId
        this.to = toId
    }

    constructor(content: String, timestamp: Long) : this() {
        this.content = content
        this.timestamp = timestamp
    }
}

fun Message.isMine(): Boolean {
    // Dummy check by id.
    return 1L == author.uid
}

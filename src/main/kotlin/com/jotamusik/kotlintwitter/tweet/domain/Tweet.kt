package com.jotamusik.kotlintwitter.tweet.domain

import com.jotamusik.kotlintwitter.user.domain.User
import java.util.*

data class Tweet(
        val id: String,
        val text: String,
        val user: User,
) {
    constructor(text: String, user: User) : this(UUID.randomUUID().toString(), text, user)
}

package com.jotamusik.kotlintwitter.user.domain

import java.util.*

data class User(
        val id: String,
        val username: String,
        val password: String,
) {
    constructor(username: String, password: String) : this(UUID.randomUUID().toString(), username, password)
}

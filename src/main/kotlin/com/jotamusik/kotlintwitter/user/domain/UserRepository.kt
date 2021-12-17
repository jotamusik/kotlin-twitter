package com.jotamusik.kotlintwitter.user.domain

import java.util.*

interface UserRepository {
    fun findByUsername(username: String): Optional<User>
}
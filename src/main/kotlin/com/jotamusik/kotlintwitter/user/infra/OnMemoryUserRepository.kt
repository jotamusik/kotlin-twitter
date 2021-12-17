package com.jotamusik.kotlintwitter.user.infra

import com.jotamusik.kotlintwitter.user.domain.User
import com.jotamusik.kotlintwitter.user.domain.UserRepository
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.ArrayList

@Repository
class OnMemoryUserRepository : UserRepository {
    val users = ArrayList<User>()

    override fun findByUsername(username: String): Optional<User> {
        return Optional.ofNullable(users.find { user -> user.username == username })
    }
}

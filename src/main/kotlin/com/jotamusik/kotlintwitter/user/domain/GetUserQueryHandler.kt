package com.jotamusik.kotlintwitter.user.domain

import org.springframework.stereotype.Component
import java.util.*

@Component
class GetUserQueryHandler(
        private val userRepository: UserRepository,
) {

    fun byUsername(username: String): Optional<User> {
        return userRepository.findByUsername(username)
    }

}

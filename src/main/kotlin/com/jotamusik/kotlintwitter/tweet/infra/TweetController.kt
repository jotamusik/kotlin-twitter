package com.jotamusik.kotlintwitter.tweet.infra

import com.jotamusik.kotlintwitter.tweet.domain.PublishTweetCommand
import com.jotamusik.kotlintwitter.tweet.domain.PublishTweetCommandHandler
import com.jotamusik.kotlintwitter.tweet.domain.PublishTweetResponse
import com.jotamusik.kotlintwitter.user.domain.GetUserQueryHandler
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/tweet")
class TweetController(
        private val publishTweetCommandHandler: PublishTweetCommandHandler,
        private val getUserQueryHandler: GetUserQueryHandler,
) {

    @PostMapping
    fun publishTweet(@RequestBody requestBody: PublishTweetRequestBody): ResponseEntity<Any> {

        val currentLoggedUsername: String = getLoggedUsername()

        val user = getUserQueryHandler.byUsername(currentLoggedUsername)

        if (user.isEmpty) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }

        val publishTweetCommand = PublishTweetCommand(requestBody.text, user.get())
        val response: PublishTweetResponse = this.publishTweetCommandHandler(publishTweetCommand)

        return if (response.isSuccess()) {
            ResponseEntity
                    .ok()
                    .header("Content-Type", "application/json")
                    .build()
        } else {
            ResponseEntity
                    .badRequest()
                    .header("Content-Type", "application/json")
                    .build()
        }
    }

    private fun getLoggedUsername(): String {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
        return authentication.name
    }
}

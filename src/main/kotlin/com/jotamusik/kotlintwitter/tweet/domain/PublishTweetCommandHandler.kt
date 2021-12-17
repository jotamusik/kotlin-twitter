package com.jotamusik.kotlintwitter.tweet.domain

import com.jotamusik.kotlintwitter.user.domain.User
import org.springframework.stereotype.Component

@Component
class PublishTweetCommandHandler(private val tweetRepository: TweetRepository) {

    operator fun invoke(publishTweetCommand: PublishTweetCommand): PublishTweetResponse {
        try {
            tweetRepository.createNewTweet(Tweet(publishTweetCommand.text, publishTweetCommand.user))
        } catch (error: Exception) {
            return PublishTweetResponse.error()
        }
        return PublishTweetResponse.success()
    }
}

open class CommandResponse protected constructor(
        private val isError: Boolean,
        private val errorMessage: String = "",
) {
    fun isError(): Boolean = isError
    fun isSuccess(): Boolean = !isError
    fun message(): String = errorMessage
}

class PublishTweetResponse private constructor(
        isError: Boolean, message: String = ""
) : CommandResponse(isError, message) {

    companion object {
        fun success(): PublishTweetResponse {
            return PublishTweetResponse(false)
        }

        fun error(): PublishTweetResponse {
            return PublishTweetResponse(true, "something went wrong")
        }
    }
}

data class PublishTweetCommand(
        val text: String,
        val user: User,
)

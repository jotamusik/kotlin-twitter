package com.jotamusik.kotlintwitter.tweet.infra

import com.jotamusik.kotlintwitter.tweet.domain.Tweet
import com.jotamusik.kotlintwitter.tweet.domain.TweetRepository
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException
import kotlin.random.Random

@Repository
class OnMemoryTweetRepository : TweetRepository {
    private val tweets = ArrayList<DBTweet>()

    override fun createNewTweet(tweet: Tweet) {
        tweets.add(DBTweet.fromTweet(tweet))
    }
}

data class DBTweet(
        val id: String,
        val text: String,
        val userId: Int,
) {

    companion object {
        fun fromTweet(tweet: Tweet): DBTweet {
            if (tweet.user.id == null) {
                throw IllegalArgumentException("User should be logged in")
            }
            return DBTweet(tweet.id, tweet.text, tweet.user.id)
        }
    }
}

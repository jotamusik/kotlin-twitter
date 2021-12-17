package com.jotamusik.kotlintwitter.tweet.domain

interface TweetRepository {
    fun createNewTweet(tweet: Tweet)
}

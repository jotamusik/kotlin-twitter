package com.jotamusik.kotlintwitter

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinTwitterApplication

fun main(args: Array<String>) {
    runApplication<KotlinTwitterApplication>(*args)
}

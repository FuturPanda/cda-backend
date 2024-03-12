package com.example.notificationbackend.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "app")
class AppProperties(
    val openAi: OpenAi,
) {
    class OpenAi(
        val model: String,
        val apiUrl: String,
        val apiKey: String
    )

}
package com.example.notificationbackend.service

import com.example.notificationbackend.config.AppProperties
import com.example.notificationbackend.web.rest.dto.ChatCompletionChunkResponse
import com.example.notificationbackend.web.rest.dto.MessagesDto
import com.example.notificationbackend.web.rest.dto.OpenAiRequest
import com.example.notificationbackend.web.rest.dto.OpenAiReturnDto
import com.fasterxml.jackson.annotation.JsonProperty
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder
import org.jsoup.Jsoup
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.server.ResponseStatusException
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@Service
class LinkService (
    private val properties: AppProperties
) {
    private val restClient = RestClient.builder()
        .baseUrl(properties.openAi.apiUrl)
        .requestFactory(HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build()))
        .build()


    fun getCompletion(url : String, stream : Boolean? = null) : OpenAiReturnDto {
        val dataFromWebsite = scrapWebsite(url)
        val data = OpenAiRequest(properties.openAi.model, listOf(
            MessagesDto("system"," You are a high level developer that write high quality daily content for education.\n" +
                "        Use the following step-by-step instructions to respond to user inputs.\n" +
                "        Step 1 -  The user will provide you with text scrapped from a website. The text is raw so some informations arn't relevant.\n" +
                "        Find what the matter of the article really is.\n" +
                "        Step 2 - Summarize this text in one sentence in English with the prefix \"tldr : \" \n" +
                "        Step 3 - Translate the summary from Step 1 into French with the prefix \"-- translation : \" \n" +
                "        Step 4 - Choose one word reflecting the broad tech category where the article lies\"-- tag : \" \n" +
                "        Step 5 - Give a fun ad-like engaging title to your summary with the prefix \"-- title : \" " ),
            MessagesDto("user", dataFromWebsite)
            ))
        val result = restClient.post()
            .uri(URI_COMPLETION)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.openAi.apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .body(data)
            .retrieve().body(OpenAiReturnDto::class.java)
            ?: throw ResponseStatusException(HttpStatus.BAD_GATEWAY, "Unable to contact openai")

        println(result.model)
        return result
    }

    fun getStream(url : String) {
        val dataFromWebsite = scrapWebsite(url)
        val data = OpenAiRequest(properties.openAi.model, listOf(
            MessagesDto("system"," You are a high level developer that write high quality daily content for education.\n" +
                    "        Use the following step-by-step instructions to respond to user inputs.\n" +
                    "        Step 1 -  The user will provide you with text scrapped from a website. The text is raw so some informations arn't relevant.\n" +
                    "        Find what the matter of the article really is.\n" +
                    "        Step 2 - Summarize this text in one sentence in English with the prefix \"tldr : \" \n" +
                    "        Step 3 - Translate the summary from Step 1 into French with the prefix \"-- translation : \" \n" +
                    "        Step 4 - Choose one word reflecting the broad tech category where the article lies\"-- tag : \" \n" +
                    "        Step 5 - Give a fun ad-like engaging title to your summary with the prefix \"-- title : \" " ),
            MessagesDto("user", dataFromWebsite)
        ),true)
        val result = restClient.post()
            .uri(URI_COMPLETION)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + properties.openAi.apiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .body(data).retrieve().body(ChatCompletionChunkResponse::class.java)?.response?.map {
                println(it)
            }
    }

    fun scrapWebsite(url : String) : String{
        val document = Jsoup.connect(url).get()
        return document.text()
    }



    companion object {
        const val URI_COMPLETION = "/v1/chat/completions"
    }
}
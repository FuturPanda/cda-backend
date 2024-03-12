package com.example.notificationbackend.web.rest

import com.example.notificationbackend.config.AppProperties
import com.example.notificationbackend.service.LinkService
import com.example.notificationbackend.web.rest.dto.OpenAiReturnDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient

@RestController
@RequestMapping("/api/v1/link")
class LinkController(
    private val linkService: LinkService
){
    @PostMapping
    fun getSummaryFromOpenAi(@RequestBody url : UrlDto) : OpenAiReturnDto {
        return linkService.getCompletion(url.url)
    }

    @PostMapping("/stream", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getStreaming(@RequestBody url : UrlDto)  {
        linkService.getStream(url.url)
    }

}


class UrlDto(
    val url : String
)
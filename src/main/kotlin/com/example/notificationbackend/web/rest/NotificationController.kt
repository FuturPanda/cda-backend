package com.example.notificationbackend.web.rest

import com.example.notificationbackend.domain.Notification
import com.example.notificationbackend.service.NotificationService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue


@RestController
@RequestMapping("/api/v1/event")
class NotificationController(
    private val notificationService: NotificationService
) {
    private var value : String? = null

    @GetMapping("/long-polling")
    fun longPolling(): DeferredResult<ResponseEntity<String>> {
        val output = DeferredResult<ResponseEntity<String>>(5000L)
        output.onTimeout {
            notificationService.removeFromQueue(output)
            output.setResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).build())
        }
        notificationService.addToQueue(output)
        return output
    }

    @PostMapping("/notify")
    fun notifyClients(@RequestBody data: String?) {
        value = data
        notificationService.notifyAll(data ?: "")
    }


    @GetMapping("/subscribe", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribeSse(): SseEmitter {
        val emitter = SseEmitter(Long.MAX_VALUE)
        notificationService.addEmitter(emitter)
        return emitter
    }

    @GetMapping("/notification")
    fun getValue() : String? {
        val temp = value
        value = null
        return temp
    }

    @GetMapping("/all")
    fun getAllNotifications() : List<Notification> {
        return emptyList()
    }
}
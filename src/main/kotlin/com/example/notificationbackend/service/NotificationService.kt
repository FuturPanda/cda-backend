package com.example.notificationbackend.service

import liquibase.util.SystemUtil
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.context.request.async.DeferredResult
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList



@Service
class NotificationService {
    private val emitters: MutableList<SseEmitter> = mutableListOf()
    private val responseBodyQueue: Queue<DeferredResult<ResponseEntity<String>>> = ConcurrentLinkedQueue()

    fun addEmitter(emitter: SseEmitter) {
        emitters.add(emitter)
        emitter.onCompletion { emitters.remove(emitter) }
        emitter.onTimeout { emitters.remove(emitter) }
    }

    fun addToQueue(result : DeferredResult<ResponseEntity<String>>) {
        responseBodyQueue.add(result)
    }
    fun removeFromQueue(output :DeferredResult<ResponseEntity<String>> ){
        responseBodyQueue.remove(output)
    }

    fun notifyAll(data : String){
        for (emitter in emitters) {
            try {
                emitter.send(data)
            } catch (e: IOException) {
                emitter.complete()
                emitters.remove(emitter)
            }
        }
        for (result in responseBodyQueue) {
            result.setResult(ResponseEntity.ok(data))
            responseBodyQueue.remove(result)
        }
    }

    @Scheduled(fixedRate = 30000)
    fun heartbeat() {
        for (emitter in emitters) {
            try {
                emitter.send("heartbeat")
            } catch (e: IOException) {
                emitter.complete()
                emitters.remove(emitter)
            }
        }
    }
}
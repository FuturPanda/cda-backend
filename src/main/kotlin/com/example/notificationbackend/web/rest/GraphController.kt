package com.example.notificationbackend.web.rest

import com.example.notificationbackend.domain.AppUser
import com.example.notificationbackend.domain.Notification
import com.example.notificationbackend.service.GraphService
import org.jsoup.HttpStatusException
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/graph")
class GraphController(
    private val graphService: GraphService
) {
    @GetMapping("/users")
    fun getUsers() : List<AppUser> {
        return graphService.findAllUsers()
    }

    @GetMapping("/users/{id}")
    fun getUsers(@PathVariable id : Long) : AppUser? {
        println("Hello controller")
        return graphService.findAppUserById(id)
    }

    @GetMapping("/notifs")
    fun getNotifs() : List<Notification> {
        return graphService.findByGraphAndSubgraph()
    }


}
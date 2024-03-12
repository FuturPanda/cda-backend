package com.example.notificationbackend.service

import com.example.notificationbackend.domain.AppUser
import com.example.notificationbackend.domain.Notification
import com.example.notificationbackend.repository.AppUserRepository
import com.example.notificationbackend.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class GraphService(
    private val appUserRepository: AppUserRepository,
    private val notificationRepository: NotificationRepository
) {
    fun findAppUserById(id: Long) : AppUser? {
        println("Hello service")
        return appUserRepository.findAppUserById(id)
    }

    fun findAllUsers() : List<AppUser> {
        return appUserRepository.findAll()
    }

    fun findAllNotifs() : List<Notification> {
        return notificationRepository.findAll()
    }

    fun findByGraphUser(){

    }
    fun findByGraphNotifs(): List<Notification> {
        return notificationRepository.findAllById(4L)
    }
    fun findByGraphAndSubgraph(): List<Notification>{
        return notificationRepository.findAllById(1)
    }

}
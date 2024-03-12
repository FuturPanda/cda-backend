package com.example.notificationbackend.repository

import com.example.notificationbackend.domain.Notification
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface NotificationRepository : JpaRepository<Notification, Long> {

    @EntityGraph(value = "Notification.appUserId", type = EntityGraph.EntityGraphType.LOAD)
    override fun findAll(): List<Notification>

    @EntityGraph(value = "Notification.documentId", type = EntityGraph.EntityGraphType.LOAD)
    fun findAllById(id : Long): List<Notification>

    @EntityGraph(value = "Notification.documentId", type = EntityGraph.EntityGraphType.LOAD)
    override fun findById(id : Long): Optional<Notification>
}
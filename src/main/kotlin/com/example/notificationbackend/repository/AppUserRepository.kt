package com.example.notificationbackend.repository

import com.example.notificationbackend.domain.AppUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AppUserRepository : JpaRepository<AppUser, Long> {
    fun findAppUserById(id : Long) : AppUser?
}
package com.example.notificationbackend.service

import com.example.notificationbackend.domain.AppUser
import com.example.notificationbackend.repository.AppUserRepository
import org.springframework.stereotype.Service

@Service
class TestService(
    private val appUserRepository: AppUserRepository
) {
    fun testGetAppUserEmail(num : Long) : AppUser? {
        return appUserRepository.findAppUserById(num)
    }
}
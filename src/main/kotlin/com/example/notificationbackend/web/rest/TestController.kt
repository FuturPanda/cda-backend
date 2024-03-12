package com.example.notificationbackend.web.rest

import com.example.notificationbackend.service.TestService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Provider

@RestController
class TestController(
    private val testService: TestService
) {
    fun isPositive(num : Int) : Boolean{
        return num > 0
    }

    @GetMapping("/hello")
    fun hello(@RequestParam name: Long) = testService.testGetAppUserEmail(name)
}


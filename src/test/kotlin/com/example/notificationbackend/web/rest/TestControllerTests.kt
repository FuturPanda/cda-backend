package com.example.notificationbackend.web.rest

import com.example.notificationbackend.config.AppProperties
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest (
    val mockMvc: MockMvc,
){
    @Test
    fun testIt(){
        mockMvc.get("http://localhost:8080/api/v1/graph/notifs")
            .andDo { print() }
            .andExpect {
                status { isOk() }
            }
    }
}



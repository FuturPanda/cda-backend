package com.example.notificationbackend.config

import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
internal class AppWebMvcConfigurer : WebMvcConfigurer {
    override fun addFormatters(registry: FormatterRegistry) {
        val registrar = DateTimeFormatterRegistrar()
        registrar.setUseIsoFormat(true)
        registrar.registerFormatters(registry)
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**").apply {
            allowedOrigins("*")
            allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS", "PATCH")
            allowedHeaders("Origin", "Content-Type", "Accept", "Authorization")
            exposedHeaders("Location")
        }
    }
}

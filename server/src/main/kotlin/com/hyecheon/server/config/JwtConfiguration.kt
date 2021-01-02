package com.hyecheon.server.config

import com.hyecheon.server.core.security.JwtAuthTokenProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JwtConfiguration {
    @Bean
    fun jwtProvider(@Value("\${jwt.base64-secret}") secret: String): JwtAuthTokenProvider {
        return JwtAuthTokenProvider(secret)
    }
}
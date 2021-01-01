package com.hyecheon.server.core.security

import org.springframework.security.core.Authentication
import java.util.*

interface AuthTokenProvider<T> {
    fun createAuthToken(id: String, role: String, expiredDate: Date): T
    fun convertAuthToken(token: String): T
    fun getAuthentication(authToken: T): Authentication
}
package com.hyecheon.server.core.security


import com.hyecheon.server.exception.TokenValidFailedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User


class JwtAuthTokenProvider(private val base64Secret: String) {
    companion object {
        const val AUTHORITIES_KEY = "role"
    }

    fun createAuthToken(id: String, role: String): JwtAuthToken {
        return JwtAuthToken(id, role, base64Secret)
    }

    fun convertAuthToken(token: String): JwtAuthToken {
        return JwtAuthToken(token, base64Secret)
    }

    fun getAuthentication(authToken: JwtAuthToken): Authentication {
        return if (authToken.validate()) {
            val claims = authToken.data ?: throw TokenValidFailedException()
            val authorities =
                arrayOf(claims[AUTHORITIES_KEY].toString()).map { SimpleGrantedAuthority(it) }
            val principal = User(claims.subject, "", authorities)
            UsernamePasswordAuthenticationToken(principal, authToken, authorities)
        } else {
            throw TokenValidFailedException()
        }
    }
}
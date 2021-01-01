package com.hyecheon.server.provider.security


import com.hyecheon.server.core.security.AuthTokenProvider
import com.hyecheon.server.exception.TokenValidFailedException
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import java.security.Key
import java.util.*


class JwtAuthTokenProvider(base64Secret: String?) : AuthTokenProvider<JwtAuthToken> {
    private val key: Key

    companion object {
        private const val AUTHORITIES_KEY = "role"
    }

    init {
        val keyBytes = Decoders.BASE64.decode(base64Secret)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    override fun createAuthToken(id: String, role: String, expiredDate: Date): JwtAuthToken {
        return JwtAuthToken(id, role, expiredDate, key)
    }

    override fun convertAuthToken(token: String): JwtAuthToken {
        return JwtAuthToken(token, key)
    }

    override fun getAuthentication(authToken: JwtAuthToken): Authentication {
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
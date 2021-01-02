package com.hyecheon.server.core.security

import com.hyecheon.server.exception.TokenValidFailedException
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import java.security.Key
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import io.jsonwebtoken.Jwts
import org.springframework.security.core.authority.SimpleGrantedAuthority


class JwtAuthToken {
    companion object {
        private const val AUTHORITIES_KEY = "role"
        private const val LOGIN_RETENTION_MINUTES: Long = 30
    }

    private val log = LoggerFactory.getLogger(this::class.java)

    val token: String
    private val key: Key


    constructor(token: String, base64Secret: String) {
        this.key = base64Secret.jwtKey()
        this.token = token
    }

    constructor(id: String, role: String, base64Secret: String) {
        this.key = base64Secret.jwtKey()
        this.token = createJwtAuthToken(id, role).get()
    }

    fun validate(): Boolean {
        return data != null
    }

    val data: Claims?
        get() {
            try {
                return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
            } catch (e: SecurityException) {
                log.info("Invalid JWT signature.")
            } catch (e: MalformedJwtException) {
                log.info("Invalid JWT token.")
            } catch (e: ExpiredJwtException) {
                log.info("Expired JWT token.")
            } catch (e: UnsupportedJwtException) {
                log.info("Unsupported JWT token.")
            } catch (e: IllegalArgumentException) {
                log.info("JWT token compact of handler are invalid.")
            }
            return null
        }

    private fun createJwtAuthToken(id: String, role: String): Optional<String> {
        println(key)
        val token: String = Jwts.builder()
            .setSubject(id)
            .claim(AUTHORITIES_KEY, role)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(LOGIN_RETENTION_MINUTES.expiredDate())
            .compact()
        return Optional.ofNullable(token)
    }

    fun generateRefreshToken(): String {
        val claims = data ?: throw TokenValidFailedException()
        val id = claims.subject
        val role = claims[AUTHORITIES_KEY].toString()
        return Jwts.builder()
            .setSubject(id)
            .claim(AUTHORITIES_KEY, role)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(LOGIN_RETENTION_MINUTES.expiredDate())
            .compact()
    }
}

fun Long.expiredDate(): Date =
    Date.from(LocalDateTime.now().plusMinutes(this).atZone(ZoneId.systemDefault()).toInstant())

fun String.jwtKey(): Key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this))
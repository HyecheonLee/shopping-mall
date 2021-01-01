package com.hyecheon.server.provider.security

import com.hyecheon.server.core.security.AuthToken
import com.hyecheon.server.exception.JwtRuntimeException
import io.jsonwebtoken.*
import org.slf4j.LoggerFactory
import java.security.Key
import java.util.*


class JwtAuthToken : AuthToken<Claims> {
    private val log = LoggerFactory.getLogger(this::class.java)

    val token: String
    private val key: Key

    constructor(token: String, key: Key) {
        this.token = token
        this.key = key
    }

    constructor(id: String, role: String, expiredDate: Date, key: Key) {
        this.key = key
        this.token = createJwtAuthToken(id, role, expiredDate).get()
    }

    override fun validate(): Boolean {
        return data != null
    }

    override val data: Claims?
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

    private fun createJwtAuthToken(id: String, role: String, expiredDate: Date): Optional<String> {
        val token: String = Jwts.builder()
            .setSubject(id)
            .claim(AUTHORITIES_KEY, role)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(expiredDate)
            .compact()
        return Optional.ofNullable(token)
    }

    companion object {
        private const val AUTHORITIES_KEY = "role"
    }
}
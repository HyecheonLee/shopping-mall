package com.hyecheon.server.security

import com.hyecheon.server.provider.security.JwtAuthTokenProvider
import org.apache.tomcat.util.net.openssl.ciphers.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class JWTFilter internal constructor(
    private val jwtAuthTokenProvider: JwtAuthTokenProvider
) : GenericFilterBean() {

    override fun doFilter(
        servletRequest: ServletRequest,
        servletResponse: ServletResponse,
        filterChain: FilterChain
    ) {
        val httpServletRequest = servletRequest as HttpServletRequest
        val token: Optional<String> = resolveToken(httpServletRequest)
        if (token.isPresent) {
            val jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token.get())
            if (jwtAuthToken.validate()) {
                val authentication = jwtAuthTokenProvider.getAuthentication(jwtAuthToken)
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun resolveToken(request: HttpServletRequest): Optional<String> {
        val authToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(authToken)) {
            Optional.of(authToken)
        } else {
            Optional.empty()
        }
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "x-auth-token"
    }
}
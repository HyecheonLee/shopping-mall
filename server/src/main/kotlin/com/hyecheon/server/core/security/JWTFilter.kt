package com.hyecheon.server.core.security

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
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
        resolveToken(httpServletRequest)?.let { jwtProcedure(it) }
        filterChain.doFilter(servletRequest, servletResponse)
    }

    private fun jwtProcedure(token: String) {
        val jwtAuthToken = jwtAuthTokenProvider.convertAuthToken(token)
        if (jwtAuthToken.validate()) {
            val authentication = jwtAuthTokenProvider.getAuthentication(jwtAuthToken)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val authToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(authToken)) authToken else null
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "x-auth-token"
    }
}
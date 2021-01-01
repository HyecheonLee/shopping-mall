package com.hyecheon.server.exception

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint {
    private val handlerExceptionResolver: HandlerExceptionResolver? = null
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        handlerExceptionResolver!!.resolveException(request, response, null, authException)
    }
}
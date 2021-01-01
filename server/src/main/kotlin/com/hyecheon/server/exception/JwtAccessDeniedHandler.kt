package com.hyecheon.server.exception

import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class JwtAccessDeniedHandler(
    val handlerExceptionResolver: HandlerExceptionResolver
) : AccessDeniedHandler {

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: org.springframework.security.access.AccessDeniedException
    ) {
        //response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());

        //동작하지 않음!!
        //throw new CustomAuthenticationException();
        handlerExceptionResolver.resolveException(request, response, null, accessDeniedException)
    }
}

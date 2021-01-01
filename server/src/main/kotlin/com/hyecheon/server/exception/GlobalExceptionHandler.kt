package com.hyecheon.server.exception

import com.hyecheon.server.core.CommonResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(CustomAuthenticationException::class)
    fun handleAuthenticationException(e: CustomAuthenticationException): ResponseEntity<CommonResponse> {
        log.info("handleAuthenticationException", e)
        val response = CommonResponse(
            code = ErrorCode.AUTHENTICATION_FAILED.code,
            message = e.message,
            status = ErrorCode.AUTHENTICATION_FAILED.status
        )
        return ResponseEntity<CommonResponse>(response, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(JwtRuntimeException::class)
    fun handleJwtException(e: JwtRuntimeException): ResponseEntity<CommonResponse> {
        log.info("handleJwtException", e)
        val response = CommonResponse(
            ErrorCode.INVALID_JWT_TOKEN.message,
            ErrorCode.INVALID_JWT_TOKEN.status,
            ErrorCode.INVALID_JWT_TOKEN.code
        )
        return ResponseEntity<CommonResponse>(response, HttpStatus.UNAUTHORIZED)
    }
}
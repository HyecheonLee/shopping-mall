package com.hyecheon.server.exception

import com.hyecheon.server.core.CommonResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(CustomAuthenticationException::class)
    fun handleAuthenticationException(e: CustomAuthenticationException): ResponseEntity<*> {
        log.info("handleAuthenticationException", e)
        val response = CommonResponse(
            e.message,
            ErrorCode.AUTHENTICATION_FAILED.status,
            ErrorCode.AUTHENTICATION_FAILED.code
        )
        return ResponseEntity(response, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(JwtRuntimeException::class)
    fun handleJwtException(e: JwtRuntimeException): ResponseEntity<*> {
        log.info("handleJwtException", e)
        val response = CommonResponse(
            ErrorCode.INVALID_JWT_TOKEN.message,
            ErrorCode.INVALID_JWT_TOKEN.status,
            ErrorCode.INVALID_JWT_TOKEN.code
        )
        return ResponseEntity(response, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(LoginFailedException::class)
    fun handleLoginFailedException(e: LoginFailedException): ResponseEntity<*> = run {
        log.info("handleLoginFailedException", e)
        ResponseEntity(
            CommonResponse(e.message, 401, "LoginFailed01"),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialsException(e: BadCredentialsException): ResponseEntity<*> =
        run {
            log.info("handleBadCredentialsException", e)
            ResponseEntity<Any>(
                CommonResponse(e.message, 401, "LoginFailed02"),
                HttpStatus.UNAUTHORIZED
            )
        }
}
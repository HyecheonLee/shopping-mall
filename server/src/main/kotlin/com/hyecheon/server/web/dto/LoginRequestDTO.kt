package com.hyecheon.server.web.dto

data class LoginRequestDTO(
    val email: String? = null,
    val password: String? = null
)
package com.hyecheon.server.web.dto

data class LoginRequestDTO(
    val email: String,
    val password: String
) {
}
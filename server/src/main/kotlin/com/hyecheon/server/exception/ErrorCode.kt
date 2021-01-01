package com.hyecheon.server.exception

enum class ErrorCode(
    val status: Int,
    val code: String,
    val message: String
) {
    AUTHENTICATION_FAILED(401, "AUTH001", " AUTHENTICATION_FAILED."), LOGIN_FAILED(
        401,
        "AUTH002",
        " LOGIN_FAILED."
    ),
    INVALID_JWT_TOKEN(401, "AUTH003", "INVALID_JWT_TOKEN.");
}
package com.hyecheon.server.core

data class CommonResponse(
    val message: String? = null,
    val status: Int = 0,
     val code: String? = null
)
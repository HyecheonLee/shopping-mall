package com.hyecheon.server.core

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
data class CommonResponse<T>(
    val data: T? = null,
    val status: Int = -1,
    val code: String? = null
)
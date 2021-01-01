package com.hyecheon.server.core.service.dto

import com.hyecheon.server.core.security.Role

data class MemberDTO(
    val id: String? = null,
    val userName: String? = null,
    val email: String? = null,
     val role: Role? = null,
)
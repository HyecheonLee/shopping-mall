package com.hyecheon.server.web.dto

import com.hyecheon.server.core.entity.Member

data class SignupMemberDTO(
    val name: String,
    val email: String,
    val password: String
) {
    fun toMember(): Member {
        return Member(name = name, email = email, password = password)
    }
}
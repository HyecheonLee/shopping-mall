package com.hyecheon.server.core.service

import com.hyecheon.server.core.security.AuthToken
import com.hyecheon.server.core.service.dto.MemberDTO


interface LoginUseCase {
    fun login(id: String, password: String): MemberDTO
    fun createAuthToken(memberDTO: MemberDTO): AuthToken<*>
}
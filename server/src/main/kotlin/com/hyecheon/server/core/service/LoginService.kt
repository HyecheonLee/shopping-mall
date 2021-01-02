package com.hyecheon.server.core.service

import com.hyecheon.server.core.repository.MemberRepository
import com.hyecheon.server.core.security.JwtAuthToken
import com.hyecheon.server.core.security.JwtAuthTokenProvider
import com.hyecheon.server.core.service.dto.MemberDTO
import com.hyecheon.server.exception.LoginFailedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class LoginService(
    private val jwtAuthTokenProvider: JwtAuthTokenProvider,
    private val authenticationManager: AuthenticationManager,
    private val memberRepository: MemberRepository
) {
    @Transactional
    fun login(email: String, password: String): MemberDTO {
        val loggedMember = memberRepository.findByEmail(email)
            .orElseThrow { throw LoginFailedException("이메일이 존재 하지 않습니다.") }
        val authenticationToken = UsernamePasswordAuthenticationToken(email, password)
        val authentication: Authentication
        try {
            authentication = authenticationManager.authenticate(authenticationToken)
        } catch (e: BadCredentialsException) {
            throw LoginFailedException("비밀번호를 확인해 주세요")
        }
        SecurityContextHolder.getContext().authentication = authentication
        val jwtAuthToken = createAuthToken(loggedMember.email!!, loggedMember.role)
        return MemberDTO.fromUserAndToken(loggedMember, jwtAuthToken.token)
    }

    fun createAuthToken(email: String, role: String): JwtAuthToken {
        return jwtAuthTokenProvider.createAuthToken(email, role)
    }
}
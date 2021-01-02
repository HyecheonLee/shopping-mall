package com.hyecheon.server.core.service

import com.hyecheon.server.core.entity.Member
import com.hyecheon.server.core.repository.MemberRepository
import com.hyecheon.server.exception.LoginFailedException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class CustomUserDetailsService(
    private val memberRepository: MemberRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val loginMember = memberRepository.findByEmail(email)
            .orElseThrow { LoginFailedException("존재하지 않는 이메일[${email}] 입니다. ") }
        return createSpringSecurityUser(loginMember)
    }

    private fun createSpringSecurityUser(member: Member): User {
        val grantedAuthorities = listOf(SimpleGrantedAuthority(member.role))
        return User(member.email, member.password, grantedAuthorities)
    }
}
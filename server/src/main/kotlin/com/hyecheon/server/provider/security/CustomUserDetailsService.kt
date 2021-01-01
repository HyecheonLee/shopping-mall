package com.hyecheon.server.provider.security

import com.hyecheon.server.core.entity.Member
import com.hyecheon.server.core.repository.MemberRepository
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
        return memberRepository.findByEmail(email)
            .map { member -> createSpringSecurityUser(member) }
            .orElseThrow { RuntimeException() }
    }

    private fun createSpringSecurityUser(member: Member): User {
        val grantedAuthorities = listOf(SimpleGrantedAuthority(member.role))
        return User(member.email, member.password, grantedAuthorities)
    }
}
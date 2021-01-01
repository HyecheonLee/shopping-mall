package com.hyecheon.server.provider.service

import com.hyecheon.server.core.security.AuthToken
import com.hyecheon.server.core.security.Role
import com.hyecheon.server.core.service.LoginUseCase
import com.hyecheon.server.core.service.dto.MemberDTO
import com.hyecheon.server.provider.security.JwtAuthTokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

import org.springframework.security.core.GrantedAuthority


@Service
class LoginService(
    private val jwtAuthTokenProvider: JwtAuthTokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder

) : LoginUseCase {
    companion object {
        private const val LOGIN_RETENTION_MINUTES: Long = 30
    }

    override fun login(email: String, password: String): MemberDTO {

        val authenticationToken =
            UsernamePasswordAuthenticationToken(email, password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication

        //TODO: 권한은 한개만 갖는다고 가정하고 구현하였는데.. 깔끔하지 않음
        val role: Role = authentication.authorities.stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .findFirst()
            .map(Role::of)
            .orElse(Role.UNKNOWN)
        return MemberDTO(userName = "eddy", email = email, role = role)
    }

    override fun createAuthToken(memberDTO: MemberDTO): AuthToken<*> {
        val expiredDate: Date = Date.from(LocalDateTime.now().plusMinutes(LOGIN_RETENTION_MINUTES).atZone(ZoneId.systemDefault()).toInstant())

        return jwtAuthTokenProvider.createAuthToken(
            memberDTO.email ?: throw RuntimeException(""),
            memberDTO.role?.code ?: throw RuntimeException(""),
            expiredDate
        )
    }
}
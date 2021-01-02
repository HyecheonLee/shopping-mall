package com.hyecheon.server.core.service

import com.hyecheon.server.core.entity.Member
import com.hyecheon.server.core.repository.MemberRepository
import com.hyecheon.server.core.service.dto.MemberDTO
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.util.*

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(member: Member): Member {
        val encodedMember = member.copy(password = passwordEncoder.encode(member.password))
        println(encodedMember)
        return memberRepository.save(encodedMember)
    }

    fun findByEmail(email: String): MemberDTO {
        return MemberDTO.fromUser(memberRepository.findByEmail(email)
            .orElseThrow { throw RuntimeException("존재하지 않는 email(${email}) 입니다.") })
    }
}
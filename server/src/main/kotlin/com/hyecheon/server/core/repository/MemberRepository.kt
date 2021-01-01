package com.hyecheon.server.core.repository

import com.hyecheon.server.core.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email:String): Optional<Member>
}
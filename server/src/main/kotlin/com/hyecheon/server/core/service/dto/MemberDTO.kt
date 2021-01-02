package com.hyecheon.server.core.service.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.hyecheon.server.core.entity.Member
import com.hyecheon.server.core.security.Role

@JsonInclude(JsonInclude.Include.NON_NULL)
data class MemberDTO(
    @JsonProperty("userId")
    val id: Long? = null,
    val name: String? = null,
    val email: String? = null,
    val role: Role? = null,
    val authToken: String? = null,
    var isAuth: Boolean = false,
) {
    @JsonProperty("isAdmin")
    fun isAdmin(): Boolean {
        return role?.code == Role.ADMIN.code
    }

    companion object {
        fun fromUserAndToken(member: Member, authToken: String): MemberDTO {
            return MemberDTO(member.id, member.name, member.email, Role.of(member.role), authToken)
        }

        fun fromUser(member: Member): MemberDTO {
            return MemberDTO(member.id, member.name, member.email, Role.of(member.role))
        }
    }
}
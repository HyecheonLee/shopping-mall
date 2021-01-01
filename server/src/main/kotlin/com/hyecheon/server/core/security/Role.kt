package com.hyecheon.server.core.security

import java.util.*

enum class Role(val code: String, val description: String) {
    ADMIN("ROLE_ADMIN", "관리자권한"), USER("ROLE_USER", "사용자권한"), UNKNOWN("UNKNOWN", "알수없는 권한");

    companion object {
        fun of(code: String): Role {
            return Arrays.stream(values())
                .filter { r: Role ->
                    r.code == code
                }
                .findAny()
                .orElse(UNKNOWN)
        }
    }
}

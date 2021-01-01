package com.hyecheon.server.web

import com.hyecheon.server.core.CommonResponse
import com.hyecheon.server.provider.security.JwtAuthToken
import com.hyecheon.server.provider.service.LoginService
import com.hyecheon.server.web.dto.LoginRequestDTO
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController(
    private val loginService: LoginService
) {

    @RequestMapping("/api/v1/login")
    fun login(@RequestBody loginRequestDTO: LoginRequestDTO) = run {
        val loggedMemberDTO = loginService.login(loginRequestDTO.email, loginRequestDTO.password)
        val jwtAuthToken = loginService.createAuthToken(loggedMemberDTO)
        if (jwtAuthToken is JwtAuthToken) {
            CommonResponse(code = "LoginSuccess", status = 200, message = jwtAuthToken.token)
        } else {
            throw RuntimeException("error")
        }
    }
}
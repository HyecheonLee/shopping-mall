package com.hyecheon.server.web

import com.hyecheon.server.core.security.JwtAuthToken
import com.hyecheon.server.core.service.LoginService
import com.hyecheon.server.core.service.MemberService
import com.hyecheon.server.core.service.dto.MemberDTO
import com.hyecheon.server.web.dto.LoginRequestDTO
import com.hyecheon.server.web.dto.SignupMemberDTO
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RequestMapping("/api/v1/users")
@RestController
class UserController(
    private val loginService: LoginService,
    private val memberService: MemberService,
    @Value("\${jwt.base64-secret}") val secret: String
) {
    @PostMapping("/register")
    fun register(@RequestBody signupMemberDTO: SignupMemberDTO) = run {
        val member = signupMemberDTO.toMember()
        val registeredMember = memberService.register(member)
        mapOf("success" to true, "member" to registeredMember)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequestDTO: LoginRequestDTO): MemberDTO = run {
        loginService.login(loginRequestDTO.email!!, loginRequestDTO.password!!)
    }

    @GetMapping("/auth")
    fun auth(request: HttpServletRequest) = run {
        val token = request.getHeader("x-auth-token")
        val jwtAuthToken = JwtAuthToken(token, secret)
        if (jwtAuthToken.validate()) {
            val email = jwtAuthToken.data!!.subject
            val memberDTO = memberService.findByEmail(email)
            memberDTO.copy(isAuth = true, authToken = jwtAuthToken.generateRefreshToken())
        } else {
            MemberDTO(isAuth = false)
        }
    }
}
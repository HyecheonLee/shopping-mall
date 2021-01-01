package com.hyecheon.server.security

import com.hyecheon.server.provider.security.JwtAuthTokenProvider
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTConfigurer(
    private val jwtAuthTokenProvider: JwtAuthTokenProvider
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val jwtFilter = JWTFilter(jwtAuthTokenProvider)
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
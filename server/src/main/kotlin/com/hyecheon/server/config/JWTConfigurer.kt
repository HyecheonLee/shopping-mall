package com.hyecheon.server.config

import com.hyecheon.server.core.security.JwtAuthTokenProvider
import com.hyecheon.server.core.security.JWTFilter
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTConfigurer(private val jwtAuthTokenProvider: JwtAuthTokenProvider) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val jwtFilter = JWTFilter(jwtAuthTokenProvider)
        builder.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
    }
}
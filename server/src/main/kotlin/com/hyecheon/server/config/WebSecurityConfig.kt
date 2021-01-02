package com.hyecheon.server.config

import com.hyecheon.server.core.security.Role
import com.hyecheon.server.exception.JwtAccessDeniedHandler
import com.hyecheon.server.exception.JwtAuthenticationEntryPoint
import com.hyecheon.server.core.security.JwtAuthTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.authentication.AuthenticationManager


@EnableWebSecurity
class WebSecurityConfig(
    private val jwtAuthTokenProvider: JwtAuthTokenProvider,
    private val authenticationErrorHandler: JwtAuthenticationEntryPoint,
    private val jwtAccessDeniedHandler: JwtAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Throws(Exception::class)
    override fun configure(httpSecurity: HttpSecurity) {
        httpSecurity
            .csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(authenticationErrorHandler)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/users/login").permitAll()
            .antMatchers("/api/v1/users/register").permitAll()
            .antMatchers("/api/v1/**").hasAnyAuthority(Role.USER.code)
            .anyRequest().authenticated()
            .and()
            .apply(securityConfigurerAdapter())
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**") // allow anonymous resource requests
            .antMatchers(
                "/", "/h2-console/**"
            )
    }

    private fun securityConfigurerAdapter(): JWTConfigurer {
        return JWTConfigurer(jwtAuthTokenProvider)
    }

    @Bean
    @Throws(java.lang.Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}
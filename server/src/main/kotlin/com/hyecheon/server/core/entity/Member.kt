package com.hyecheon.server.core.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
data class Member(
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,

    @Column(length = 50, unique = true)
    @NotNull
    @Size(min = 4, max = 50)
    val username: String? = null,

    @JsonIgnore
    @Column(length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    val password: String? = null,

    @NotNull
    @Column(length = 50, unique = true)
    @Size(min = 4, max = 50)
    val email: String? = null,

    @NotNull
    @Column(length = 50)
    @Size(min = 4, max = 50)
    val role: String? = null,
)

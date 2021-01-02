package com.hyecheon.server.core.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
data class Member(
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 64)
    @NotNull
    @Size(min = 4, max = 50)
    val name: String? = null,

    @NotNull
    @Column(length = 64, unique = true)
    @Size(min = 4, max = 50)
    val email: String? = null,

    @JsonIgnore
    @Column(length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    val password: String? = null,

    @JsonIgnore
    @Column(length = 100)
    @NotNull
    @Size(min = 4, max = 100)
    val lastName: String? = null,

    @Column(length = 50)
    @Size(min = 4, max = 50)
    val role: String = "ROLE_USER",

    @Lob
    val image: String? = null,
)

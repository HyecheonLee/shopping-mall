package com.hyecheon.server.core.security

interface AuthToken<T> {
    fun validate(): Boolean
    val data: T?
}
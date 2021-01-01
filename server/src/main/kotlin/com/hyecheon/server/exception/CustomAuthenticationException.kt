package com.hyecheon.server.exception

class CustomAuthenticationException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
}
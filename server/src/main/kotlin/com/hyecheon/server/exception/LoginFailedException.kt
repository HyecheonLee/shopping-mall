package com.hyecheon.server.exception

class LoginFailedException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
}
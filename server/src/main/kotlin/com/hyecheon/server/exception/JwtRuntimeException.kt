package com.hyecheon.server.exception

class JwtRuntimeException : RuntimeException {
    constructor() : super(ErrorCode.AUTHENTICATION_FAILED.message) {}
    constructor(ex: Exception?) : super(ex) {}
}
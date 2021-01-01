package com.hyecheon.server.exception

import java.lang.RuntimeException

class TokenValidFailedException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
}
package ru.learnsql.authorization.domain

import ru.learnsql.authorization.data.dto.TokenErrorBody
import java.io.IOException

// These exceptions may be thrown from an interceptor, that's why they must extend IOException. Otherwise OkHttp is unable to correctly process them.
open class AuthenticationException(message: String, cause: Throwable? = null) : IOException(message, cause)

class MissingRefreshTokenException(message: String, cause: Throwable? = null) : AuthenticationException(message, cause)

open class TokenRequestException(message: String, val error: String?, val description: String?, cause: Throwable? = null) :
    AuthenticationException(message, cause) {
    internal constructor(message: String, tokenErrorBody: TokenErrorBody, cause: Throwable?) :
            this(message, tokenErrorBody.error, tokenErrorBody.errorDescription, cause)
}

class InvalidCredentialsException internal constructor(tokenErrorBody: TokenErrorBody, cause: Throwable? = null) :
    TokenRequestException("Invalid credentials entered", tokenErrorBody, cause)
package ru.learnsql.authorization.data

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import ru.learnsql.app_api.const.TEXT_PLAIN
import ru.learnsql.app_api.state.TokenState
import ru.learnsql.authorization.data.dto.TokenErrorBody
import ru.learnsql.authorization.domain.InvalidCredentialsException
import ru.learnsql.authorization.domain.TokenRequestException
import javax.inject.Inject

internal val authenticationLock = Mutex()

private fun parseTokenError(cause: Exception, errorBody: TokenErrorBody) = with(errorBody) {
    if (error == "authorize.wrong_credentials") {
        InvalidCredentialsException(this, cause)
    } else {
        TokenRequestException("Request error when getting a token", error, errorDescription, cause)
    }
}

internal class AuthRepository @Inject constructor(
    private val tokenState: TokenState,
    private val authorizationNetworkApi: AuthorizationNetworkApi
) {
    suspend fun login(username: String, password: String) = authenticationLock.withLock {
        val (accessToken, refreshToken) =
            runRequestAndParseErrors(TokenErrorBody::class.java, ::parseTokenError) {
                authorizationNetworkApi.login(
                    username.toRequestBody(TEXT_PLAIN.toMediaType()),
                    password.toRequestBody(
                        TEXT_PLAIN.toMediaType()
                    )
                )
            }
        validateAndStoreTokens(accessToken, refreshToken)

        accessToken
    }

    private fun validateAndStoreTokens(accessToken: String, refreshToken: String?) {
        tokenState.setTokens(accessToken, refreshToken)
    }

    fun authenticateByRefreshToken(): String {
        TODO()
    }
}
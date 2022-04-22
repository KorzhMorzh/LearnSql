package ru.learnsql.authorization.domain

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.learnsql.app_api.state.TokenState
import ru.learnsql.authorization.data.AuthRepository
import java.net.HttpURLConnection
import javax.inject.Inject

private const val TRY_COUNT = 4
private const val RETRY_DELAY_START = 10_000L
private const val RETRY_DELAY_MAX = 40_000L

private val Response.isAuthenticationSuccessful
    get() = code != HttpURLConnection.HTTP_UNAUTHORIZED

// The lock can be made local to the object if we guarantee there's only one token interceptor per each auth server
private val mutex = Mutex()

internal class TokenInterceptor @Inject internal constructor(
    private val tokenState: TokenState,
    private val tokenRepository: AuthRepository
) : Interceptor, Authenticator {
    override fun intercept(chain: Interceptor.Chain): Response {
        fun proceedWithTokens(accessToken: String): Response {
            val request = chain.request()
                .newBuilder()
                .header(HEADER_ACCESS_TOKEN, "Bearer $accessToken")
                .build()

            return chain.proceed(request)
        }

        val accessToken = tokenState.accessToken

        if (!accessToken.isNullOrBlank()) {
            val response = proceedWithTokens(accessToken)
            if (response.isAuthenticationSuccessful) return response

            response.body?.close()
        }

        val newAccessToken = runBlocking { refreshTokensIfNeeded(accessToken) }

        val response = proceedWithTokens(newAccessToken)
        if (response.isAuthenticationSuccessful) return response

        response.body?.close()
        throw AuthenticationException("Tokens were not accepted after refreshing, returned code ${response.code}")
    }

    private suspend fun refreshTokensIfNeeded(accessToken: String?) = mutex.withLock {
        val currentAccessToken = tokenState.accessToken
        if (currentAccessToken != null && currentAccessToken != accessToken) {
            // Some other thread has just refreshed these tokens while we were using old ones
            currentAccessToken
        } else {
            refreshToken()
        }
    }

    private suspend fun refreshToken(): String {
        lateinit var lastException: Exception

        var delayDuration = RETRY_DELAY_START
        for (iteration in 0 until TRY_COUNT) {
            if (iteration > 0) {
                delay(delayDuration)
                delayDuration = (delayDuration * 2).coerceAtMost(RETRY_DELAY_MAX)
            }

            try {
                return tokenRepository.authenticateByRefreshToken()
            } catch (ex: Exception) {
                lastException = ex

                // If we don't have the refresh token, we can't conjure it up, so there's no point in retrying
                if (ex is MissingRefreshTokenException) break
            }
        }

        throw (lastException as? AuthenticationException)
            ?: AuthenticationException("Couldn't refresh the token as the server refused the operation", lastException)
    }

    override fun authenticate(route: Route?, response: Response): Request {
        var accessToken = tokenState.accessToken

        val newAccessToken = runBlocking { refreshTokensIfNeeded(accessToken) }
        accessToken = newAccessToken

        return response
            .request
            .newBuilder()
            .header(HEADER_ACCESS_TOKEN, "Bearer $accessToken")
            .build()
    }
}
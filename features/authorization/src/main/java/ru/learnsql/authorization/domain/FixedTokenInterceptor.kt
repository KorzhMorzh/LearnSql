package ru.learnsql.authorization.domain

import okhttp3.Interceptor
import okhttp3.Response

internal class FixedTokenInterceptor(private val accessToken: String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().run {
            accessToken?.let { header(HEADER_ACCESS_TOKEN, "Bearer $accessToken") }
            build()
        }
        return chain.proceed(request)
    }
}
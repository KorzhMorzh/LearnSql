package ru.learnsql.app_api.state


interface TokenState {
    val accessToken: String?
    val refreshToken: String?

    fun setTokens(accessToken: String?, refreshToken: String?)
    fun hasToken(): Boolean
    fun clear()
}
package ru.learnsql.app_api.state


interface TokenState {
    val accessToken: String?
    val idToken: String?
    val refreshToken: String?

    fun setTokens(accessToken: String?, idToken: String?, refreshToken: String?)
    fun hasTokens(): Boolean
    fun clear()
}
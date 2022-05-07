package ru.learnsql.mobile.state

import android.content.Context
import ru.learnsql.app_api.state.TokenState

private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
private const val KEY_REFRESH_TOKEN = "REFRESH_TOKEN"
private const val STORAGE_NAME = "SHARED_PREFERENCE"

class TokenStateImpl(context: Context) : TokenState {
    private val sharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    override var accessToken: String?
        get() = sharedPref.getString(KEY_ACCESS_TOKEN, null)
        private set(value) {
            sharedPref.edit().putString(KEY_ACCESS_TOKEN, value).apply()
        }

    override var refreshToken: String?
        get() = sharedPref.getString(KEY_REFRESH_TOKEN, null)
        private set(value) {
            sharedPref.edit().putString(KEY_REFRESH_TOKEN, value).apply()
        }

    @Synchronized
    override fun setTokens(accessToken: String?, refreshToken: String?) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }

    @Synchronized
    override fun hasToken() = !accessToken.isNullOrBlank()

    @Synchronized
    override fun clear() {
        accessToken = null
        refreshToken = null
    }
}
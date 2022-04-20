package ru.learnsql.mobile.state

import android.content.Context
import ru.learnsql.app_api.state.TokenState

private const val KEY_ACCESS_TOKEN = "ACCESS_TOKEN"
private const val KEY_ID_TOKEN = "ID_TOKEN"
private const val KEY_REFRESH_TOKEN = "REFRESH_TOKEN"
private const val STORAGE_NAME = "SHARED_PREFERENCE"

class TokenStateImpl(context: Context) : TokenState {
    private val sharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    override var accessToken: String? = sharedPref.getString(KEY_ACCESS_TOKEN, null)
        private set(value) {
            sharedPref.edit().putString(KEY_ACCESS_TOKEN, value).apply()
        }

    override var idToken: String? = sharedPref.getString(KEY_ID_TOKEN, null)
        private set(value) {
            sharedPref.edit().putString(KEY_ID_TOKEN, value).apply()
        }

    override var refreshToken: String? = sharedPref.getString(KEY_REFRESH_TOKEN, null)
        private set(value) {
            sharedPref.edit().putString(KEY_REFRESH_TOKEN, value).apply()
        }

    @Synchronized
    override fun setTokens(accessToken: String?, idToken: String?, refreshToken: String?) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        this.idToken = idToken
    }

    @Synchronized
    override fun hasTokens() = !accessToken.isNullOrBlank() && !idToken.isNullOrBlank()

    @Synchronized
    override fun clear() {
        accessToken = null
        refreshToken = null
        idToken = null
    }
}
package ru.learnsql.mobile.state

import android.content.Context
import com.google.gson.Gson
import ru.learnsql.app_api.UserInfo
import ru.learnsql.app_api.state.UserInfoState

private const val KEY_USER_INFO = "user_info"
private const val STORAGE_NAME = "SHARED_PREFERENCE"

class UserInfoStateImpl(
    private val context: Context,
    private val gson: Gson,
) : UserInfoState {
    private val sharedPref = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)

    private var currentUserInfo: UserInfo?
        get() = gson.fromJson(sharedPref.getString(KEY_USER_INFO, "null"), UserInfo::class.java)
        set(value) {
            sharedPref.edit().putString(KEY_USER_INFO, gson.toJson(value)).apply()
        }

    override var current: UserInfo
        get() = currentUserInfo ?: error("User info hasn't been loaded")
        set(value) {
            currentUserInfo = value
        }

    override fun clear() {
        currentUserInfo = null
    }
}
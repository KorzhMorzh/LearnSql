package ru.learnsql.mobile.state

import android.content.Context
import ru.learnsql.app_api.UserInfo
import ru.learnsql.app_api.state.UserInfoState

private const val KEY_USER_INFO = "user_info"

class UserInfoStateImpl(private val context: Context, private val tokenStateImpl: TokenStateImpl) : UserInfoState {

    // todo save user info into storage
    // todo initial load user info
    private var currentUserInfo: UserInfo? = null
        set(value) {
            field = value
        }

    override val current: UserInfo
        get() = currentUserInfo ?: error("User info hasn't been loaded")

    override suspend fun load() {
        // todo
    }
}
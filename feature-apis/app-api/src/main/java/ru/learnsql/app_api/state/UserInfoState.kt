package ru.learnsql.app_api.state

import ru.learnsql.app_api.UserInfo

interface UserInfoState {
    val current: UserInfo

    suspend fun load()
}
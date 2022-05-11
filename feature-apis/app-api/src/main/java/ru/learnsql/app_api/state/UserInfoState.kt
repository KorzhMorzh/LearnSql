package ru.learnsql.app_api.state

import ru.learnsql.app_api.UserInfo

interface UserInfoState {
    var current: UserInfo

    fun clear()
}
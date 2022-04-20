package ru.learnsql.app_api.state

interface StateProvider {
    val token: TokenState
    val userInfo: UserInfoState

    fun clearStateInfo()
}
package ru.learnsql.app_api

import android.content.Context
import ru.learnsql.app_api.state.StateProvider
import ru.learnsql.app_api.state.TokenState
import ru.learnsql.app_api.state.UserInfoState

interface AppComponentApi {
    fun retrofitFactory(): RetrofitFactory
    fun buildInfo(): BuildInfo
    fun tokenState(): TokenState
    fun userInfoState(): UserInfoState
    fun stateProvider(): StateProvider
    fun context(): Context
}
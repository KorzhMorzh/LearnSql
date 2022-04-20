package ru.learnsql.app_api

import android.content.Context
import ru.learnsql.app_api.state.TokenState

interface AppComponentApi {
    fun retrofitFactory(): RetrofitFactory
    fun buildInfo(): BuildInfo
    fun tokenState(): TokenState
    fun context(): Context
}
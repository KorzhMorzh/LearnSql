package ru.learnsql.mobile.apiproviders

import android.content.Context
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.mobile.di.DaggerAppComponent

fun initAppComponentApi(context: Context) = object : ApiProvider<AppComponentApi> {
    override fun provide() = DaggerAppComponent.factory().create(context)
}
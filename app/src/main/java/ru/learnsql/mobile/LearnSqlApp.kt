package ru.learnsql.mobile

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.AppProvider
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.mobile.apiproviders.initAppComponentApi
import ru.learnsql.mobile.apiproviders.initComponentFactory

@HiltAndroidApp
class LearnSqlApp : Application(), AppProvider {
    override lateinit var appComponentApi: AppComponentApi
    @Volatile override lateinit var componentApiFactory: ComponentApiFactory

    override fun onCreate() {
        super.onCreate()
        appComponentApi = initAppComponentApi(this).provide()

        initComponentFactory()
    }

    override fun resetComponentApiFactory() {
        initComponentFactory()
    }
}
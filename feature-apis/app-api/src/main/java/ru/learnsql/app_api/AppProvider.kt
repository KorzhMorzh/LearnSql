package ru.learnsql.app_api

interface AppProvider {
    val appComponentApi: AppComponentApi
    val componentApiFactory: ComponentApiFactory

    fun resetComponentApiFactory()
}

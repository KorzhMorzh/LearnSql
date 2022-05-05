package ru.learnsql.mobile.apiproviders

import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.navigation.di.DaggerNavigationExposeComponent
import ru.learnsql.navigation_api.NavigationApi

fun provideNavigationApi(factory: ComponentApiFactory): ApiProvider<NavigationApi> =
    DaggerNavigationExposeComponent.factory().create(
        requireApi(factory[AppComponentApi::class]),
    )
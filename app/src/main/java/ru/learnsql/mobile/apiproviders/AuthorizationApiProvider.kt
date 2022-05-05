package ru.learnsql.mobile.apiproviders

import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.authorization.di.DaggerAuthorizationComponent
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.navigation_api.NavigationApi

fun provideAuthorizationApi(factory: ComponentApiFactory): ApiProvider<AuthorizationApi> =
    DaggerAuthorizationComponent.factory().create(
        requireApi(factory[AppComponentApi::class]),
        requireApi(factory[NavigationApi::class])
    )
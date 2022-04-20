package ru.learnsql.mobile.apiproviders

import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.authorization.di.DaggerAuthorizationExposeComponent
import ru.learnsql.authorizationapi.AuthorizationApi

fun provideAuthorizationApi(factory: ComponentApiFactory): ApiProvider<AuthorizationApi> =
    DaggerAuthorizationExposeComponent.factory().create()
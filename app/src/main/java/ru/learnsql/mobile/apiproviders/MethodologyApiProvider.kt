package ru.learnsql.mobile.apiproviders

import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.methodic.di.DaggerMethodologyExposeComponent
import ru.learnsql.methodic_api.MethodologyApi

fun provideMethodologyApi(factory: ComponentApiFactory): ApiProvider<MethodologyApi> =
    DaggerMethodologyExposeComponent.factory().create(
        requireApi(factory[AppComponentApi::class]),
    )
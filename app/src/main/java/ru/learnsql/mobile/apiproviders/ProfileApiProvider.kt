package ru.learnsql.mobile.apiproviders

import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.profile.di.DaggerProfileExposeComponent
import ru.learnsql.profile_api.ProfileApi

fun provideProfileApi(factory: ComponentApiFactory): ApiProvider<ProfileApi> =
    DaggerProfileExposeComponent.factory().create(
        requireApi(factory[AppComponentApi::class]),
    )
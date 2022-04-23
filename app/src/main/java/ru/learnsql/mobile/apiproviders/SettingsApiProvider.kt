package ru.learnsql.mobile.apiproviders

import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.settings.di.DaggerSettingsExposeComponent
import ru.learnsql.settings_api.SettingsApi

fun provideSettingsApi(factory: ComponentApiFactory): ApiProvider<SettingsApi> =
    DaggerSettingsExposeComponent.factory().create(
        requireApi(factory[AppComponentApi::class]),
    )
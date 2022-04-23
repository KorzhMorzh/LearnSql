package ru.learnsql.settings.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.settings_api.SettingsApi

@Component(
    modules = [
        SettingsModule::class,
        SettingsBindsModule::class
    ]
)
internal interface SettingsComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi
        ): SettingsComponent
    }
}

@Component(
    modules = [
        SettingsBindsModule::class
    ]
)
interface SettingsExposeComponent : ApiProvider<SettingsApi> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
        ): SettingsExposeComponent
    }
}
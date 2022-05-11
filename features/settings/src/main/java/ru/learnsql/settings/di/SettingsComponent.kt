package ru.learnsql.settings.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ViewModelModule
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.navigation_api.NavigationApi
import ru.learnsql.settings.presentation.SettingsFragment
import ru.learnsql.settings.presentation.feedback.FeedbackFragment
import ru.learnsql.settings_api.SettingsApi

@Component(
    modules = [
        SettingsModule::class,
        SettingsBindsModule::class,
        ViewModelModule::class
    ]
)
internal interface SettingsComponent {
    fun inject(settingsFragment: SettingsFragment)
    fun inject(settingsFragment: FeedbackFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi,
            @BindsInstance navigationApi: NavigationApi,
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
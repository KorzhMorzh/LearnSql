package ru.learnsql.authorization.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ViewModelModule
import ru.learnsql.authorization.presentation.AuthorizationFragment
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.navigation_api.NavigationApi

@Component(
    modules = [
        AuthorizationModule::class,
        AuthorizationBindsModule::class,
        ViewModelModule::class
    ]
)
interface AuthorizationComponent: ApiProvider<AuthorizationApi> {
    fun inject(authorizationFragment: AuthorizationFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance navigationApi: NavigationApi,
        ): AuthorizationComponent
    }
}
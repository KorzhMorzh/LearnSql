package ru.learnsql.navigation.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.navigation.NavigationActivity
import ru.learnsql.navigation_api.NavigationApi

@Component(
    modules = [
        NavigationModule::class,
        NavigationBindsModule::class
    ]
)
internal interface NavigationComponent {
    fun inject(navigationActivity: NavigationActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi
        ): NavigationComponent
    }
}
@Component(
    modules = [
        NavigationBindsModule::class
    ]
)
interface NavigationExposeComponent : ApiProvider<NavigationApi> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
        ): NavigationExposeComponent
    }
}
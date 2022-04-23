package ru.learnsql.methodic.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.methodic_api.MethodologyApi

@Component(
    modules = [
        MethodologyModule::class,
        MethodologyBindsModule::class
    ]
)
internal interface MethodologyComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi
        ): MethodologyComponent
    }
}

@Component(
    modules = [
        MethodologyBindsModule::class
    ]
)
interface MethodologyExposeComponent : ApiProvider<MethodologyApi> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
        ): MethodologyExposeComponent
    }
}
package ru.learnsql.profile.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.profile_api.ProfileApi

@Component(
    modules = [
        ProfileModule::class,
        ProfileBindsModule::class
    ]
)
internal interface ProfileComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi
        ): ProfileComponent
    }
}

@Component(
    modules = [
        ProfileBindsModule::class
    ]
)
interface ProfileExposeComponent : ApiProvider<ProfileApi> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
        ): ProfileExposeComponent
    }
}
package ru.learnsql.authorization.di

import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.authorizationapi.AuthorizationApi

@Component()
interface AuthorizationComponent {
    @Component.Factory
    interface Factory {
        fun create(
        ): AuthorizationComponent
    }
}

@Component
interface AuthorizationExposeComponent : ApiProvider<AuthorizationApi> {

    @Component.Factory
    interface Factory {
        fun create(): AuthorizationExposeComponent
    }
}
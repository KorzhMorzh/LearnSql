package ru.learnsql.authorization.di

import dagger.Module
import dagger.Provides
import ru.learnsql.authorization.AuthorizationApiImpl
import ru.learnsql.authorizationapi.AuthorizationApi

@Module
class AuthorizationModule {
    @Provides
    fun provideAuthorizationApi(): AuthorizationApi = AuthorizationApiImpl()
}
package ru.learnsql.mobile.di

import dagger.Component
import ru.learnsql.app_api.AppComponentApi
import javax.inject.Qualifier
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@AppScope
@Singleton
@Component(
    modules = []
)
interface AppComponent : AppComponentApi {

    @Component.Factory
    interface Factory {
        fun create(): AppComponent
    }
}
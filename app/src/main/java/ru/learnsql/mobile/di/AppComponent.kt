package ru.learnsql.mobile.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.AppComponentApi
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@AppScope
@Singleton
@Component(
    modules = [AppModule::class, States::class, BindingsAppModule::class]
)
interface AppComponent : AppComponentApi {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}
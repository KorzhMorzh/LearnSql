package ru.learnsql.mobile.di

import android.content.Context
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.learnsql.app_api.BuildInfo
import ru.learnsql.app_api.RetrofitFactory
import ru.learnsql.app_api.state.StateProvider
import ru.learnsql.app_api.state.TokenState
import ru.learnsql.appnetwork.RetrofitFactoryImpl
import ru.learnsql.mobile.state.StateProviderImpl
import javax.inject.Singleton

@Module
internal class AppModule {
    @Provides
    @Singleton
    fun provideRetrofitFactory(context: Context, buildInfo: BuildInfo): RetrofitFactory =
        RetrofitFactoryImpl(context, buildInfo)
}

@Module
internal abstract class BindingsAppModule {
    @Binds
    @Singleton
    abstract fun provideStateProvider(stateProvider: StateProviderImpl): StateProvider
}

@Module
internal class States {

    @Provides
    fun provideGson(): Gson = Gson()

    @Provides
    fun provideBuildInfo(): BuildInfo = BuildInfo

    @Provides
    fun provideTokenState(stateProvider: StateProvider): TokenState = stateProvider.token
}
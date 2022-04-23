package ru.learnsql.settings.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.settings.SettingsApiImpl
import ru.learnsql.settings_api.SettingsApi

@Module
internal class SettingsModule {
    //    @Provides
    //    fun authorizationNetworkApi(retrofit: Retrofit): AuthorizationNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()
}

@Module
internal abstract class SettingsBindsModule {
    //    @Binds
    //    @IntoMap
    //    @ViewModelKey(AuthorizationViewModel::class)
    //    abstract fun provideAuthorizationVM(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    abstract fun provideSettingsApi(SettingsApiImpl: SettingsApiImpl): SettingsApi
}
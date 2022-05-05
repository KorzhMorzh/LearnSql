package ru.learnsql.navigation.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.navigation.NavigationApiImpl
import ru.learnsql.navigation_api.NavigationApi

@Module
internal class NavigationModule {
    //    @Provides
    //    fun authorizationNetworkApi(retrofit: Retrofit): AuthorizationNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()
}

@Module
internal abstract class NavigationBindsModule {
    //    @Binds
    //    @IntoMap
    //    @ViewModelKey(AuthorizationViewModel::class)
    //    abstract fun provideAuthorizationVM(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    abstract fun provideNavigationApi(NavigationApiImpl: NavigationApiImpl): NavigationApi
}
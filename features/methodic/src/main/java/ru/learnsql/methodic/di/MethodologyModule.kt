package ru.learnsql.methodic.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.methodic.MethodologyApiImpl
import ru.learnsql.methodic_api.MethodologyApi

@Module
internal class MethodologyModule {
    //    @Provides
    //    fun authorizationNetworkApi(retrofit: Retrofit): AuthorizationNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()
}

@Module
internal abstract class MethodologyBindsModule {
    //    @Binds
    //    @IntoMap
    //    @ViewModelKey(AuthorizationViewModel::class)
    //    abstract fun provideAuthorizationVM(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    abstract fun provideMethodologyApi(methodologyApiImpl: MethodologyApiImpl): MethodologyApi
}
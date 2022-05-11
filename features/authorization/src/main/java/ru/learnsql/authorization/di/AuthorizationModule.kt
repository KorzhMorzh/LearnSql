package ru.learnsql.authorization.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.BuildInfo
import ru.learnsql.app_api.ViewModelKey
import ru.learnsql.authorization.AuthorizationApiImpl
import ru.learnsql.authorization.data.AuthorizationNetworkApi
import ru.learnsql.authorization.domain.TokenInterceptor
import ru.learnsql.authorization.presentation.AuthorizationViewModel
import ru.learnsql.authorizationapi.AuthorizationApi
import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.BINARY

@Qualifier
@Retention(BINARY)
internal annotation class Authenticated

private fun createAuthenticatedClient(componentApi: AppComponentApi, tokenInterceptor: Interceptor) = componentApi.retrofitFactory()
    .okHttpBuilder()
    .addInterceptor(tokenInterceptor)
    .build()

private fun createRetrofit(buildInfo: BuildInfo, okHttpClient: OkHttpClient) = Builder()
    .baseUrl(buildInfo.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()

@Module
internal class AuthorizationModule {

    @Provides
    fun authorizationNetworkApi(retrofit: Retrofit): AuthorizationNetworkApi = retrofit.create()

    @Provides
    fun provideRetrofit(appComponentApi: AppComponentApi): Retrofit {
        return appComponentApi.retrofitFactory().retrofitBuilder().build()
    }

    @Provides
    @Reusable
    fun provideTokenState(componentApi: AppComponentApi) = componentApi.tokenState()

    @Provides
    @Reusable
    fun provideUserInfoState(componentApi: AppComponentApi) = componentApi.userInfoState()

    @Provides
    @Reusable
    fun provideStateProvider(componentApi: AppComponentApi) = componentApi.stateProvider()

    @Provides
    @Authenticated
    fun provideAuthenticatedOkHttp(componentApi: AppComponentApi, tokenInterceptor: TokenInterceptor) =
        createAuthenticatedClient(componentApi, tokenInterceptor)

    @Provides
    @Authenticated
    fun provideAuthenticatedRetrofit(@Authenticated okHttpClient: OkHttpClient, buildInfo: BuildInfo): Retrofit =
        createRetrofit(buildInfo, okHttpClient)

    @Provides
    fun provideBuildInfo(): BuildInfo = BuildInfo
}

@Module
internal abstract class AuthorizationBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    abstract fun provideAuthorizationVM(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    abstract fun provideAuthApi(authApiImpl: AuthorizationApiImpl): AuthorizationApi
}
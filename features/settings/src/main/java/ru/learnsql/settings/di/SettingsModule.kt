package ru.learnsql.settings.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ViewModelKey
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.settings.SettingsApiImpl
import ru.learnsql.settings.data.FeedbackNetworkApi
import ru.learnsql.settings.presentation.SettingsViewModel
import ru.learnsql.settings.presentation.feedback.FeedbackViewModel
import ru.learnsql.settings_api.SettingsApi
import javax.inject.Named

@Module
internal class SettingsModule {
    @Provides
    fun feedbackNetworkApi(retrofit: Retrofit): FeedbackNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()

    @Provides
    @Reusable
    @Named("userId")
    fun provideUserId(componentApi: AppComponentApi) = componentApi.userInfoState().current.id
}

@Module
internal abstract class SettingsBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun provideSettingsVM(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedbackViewModel::class)
    abstract fun provideFeedbackVM(viewModel: FeedbackViewModel): ViewModel

    @Binds
    abstract fun provideSettingsApi(SettingsApiImpl: SettingsApiImpl): SettingsApi
}
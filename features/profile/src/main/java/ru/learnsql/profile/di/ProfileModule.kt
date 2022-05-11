package ru.learnsql.profile.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.UserInfo
import ru.learnsql.app_api.ViewModelKey
import ru.learnsql.app_api.state.UserInfoState
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.profile.ProfileApiImpl
import ru.learnsql.profile.data.ProfileNetworkApi
import ru.learnsql.profile.presentation.ProfileViewModel
import ru.learnsql.profile.presentation.editpassword.EditPasswordViewModel
import ru.learnsql.profile_api.ProfileApi

@Module
internal class ProfileModule {
    @Provides
    fun profileNetworkApi(retrofit: Retrofit): ProfileNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()

    @Provides
    fun provideUserInfo(appComponentApi: AppComponentApi): UserInfo = appComponentApi.userInfoState().current

    @Provides
    fun provideUserInfoState(appComponentApi: AppComponentApi): UserInfoState = appComponentApi.userInfoState()
}

@Module
internal abstract class ProfileBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun provideProfileVM(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditPasswordViewModel::class)
    abstract fun provideEditPasswordVM(viewModel: EditPasswordViewModel): ViewModel

    @Binds
    abstract fun provideProfileApi(ProfileApiImpl: ProfileApiImpl): ProfileApi
}
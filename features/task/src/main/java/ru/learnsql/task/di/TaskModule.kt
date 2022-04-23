package ru.learnsql.task.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.task.TaskApiImpl
import ru.learnsql.task_api.TaskApi

@Module
internal class TaskModule {
    //    @Provides
    //    fun authorizationNetworkApi(retrofit: Retrofit): AuthorizationNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()
}

@Module
internal abstract class TaskBindsModule {
    //    @Binds
    //    @IntoMap
    //    @ViewModelKey(AuthorizationViewModel::class)
    //    abstract fun provideAuthorizationVM(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    abstract fun provideTaskApi(TaskApiImpl: TaskApiImpl): TaskApi
}
package ru.learnsql.task.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.create
import ru.learnsql.app_api.ViewModelKey
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.core.AssistedSavedStateViewModelFactory
import ru.learnsql.task.TaskApiImpl
import ru.learnsql.task.data.TaskNetworkApi
import ru.learnsql.task.presentation.TaskViewModel
import ru.learnsql.task_api.TaskApi

@Module
internal class TaskModule {
    @Provides
    fun taskNetworkApi(retrofit: Retrofit): TaskNetworkApi = retrofit.create()

    @Provides
    fun provideAuthenticatedRetrofit(authorizationApi: AuthorizationApi): Retrofit =
        authorizationApi.getRetrofit()
}

@Module
internal abstract class TaskBindsModule {
    @Binds
    @IntoMap
    @ViewModelKey(TaskViewModel::class)
    abstract fun provideTaskVM(factory: TaskViewModel.Factory): AssistedSavedStateViewModelFactory

    @Binds
    abstract fun provideTaskApi(TaskApiImpl: TaskApiImpl): TaskApi
}
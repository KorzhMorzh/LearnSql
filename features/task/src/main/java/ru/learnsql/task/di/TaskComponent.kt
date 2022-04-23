package ru.learnsql.task.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.task_api.TaskApi

@Component(
    modules = [
        TaskModule::class,
        TaskBindsModule::class
    ]
)
internal interface TaskComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi
        ): TaskComponent
    }
}

@Component(
    modules = [
        TaskBindsModule::class
    ]
)
interface TaskExposeComponent : ApiProvider<TaskApi> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
        ): TaskExposeComponent
    }
}
package ru.learnsql.mobile.apiproviders

import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.task.di.DaggerTaskExposeComponent
import ru.learnsql.task_api.TaskApi

fun provideTaskApi(factory: ComponentApiFactory): ApiProvider<TaskApi> =
    DaggerTaskExposeComponent.factory().create(
        requireApi(factory[AppComponentApi::class]),
    )
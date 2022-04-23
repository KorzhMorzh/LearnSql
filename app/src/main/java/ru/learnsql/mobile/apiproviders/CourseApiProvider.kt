package ru.learnsql.mobile.apiproviders

import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.app_api.ComponentApiFactory
import ru.learnsql.app_api.requireApi
import ru.learnsql.course.di.DaggerCourseExposeComponent
import ru.learnsql.courses_api.CoursesApi

fun provideCourseApi(factory: ComponentApiFactory): ApiProvider<CoursesApi> =
    DaggerCourseExposeComponent.factory().create(
        requireApi(factory[AppComponentApi::class]),
    )
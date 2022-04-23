package ru.learnsql.course.di

import dagger.BindsInstance
import dagger.Component
import ru.learnsql.app_api.ApiProvider
import ru.learnsql.app_api.AppComponentApi
import ru.learnsql.authorizationapi.AuthorizationApi
import ru.learnsql.courses_api.CoursesApi

@Component(
    modules = [
        CourseModule::class,
        CourseBindsModule::class
    ]
)
internal interface CourseComponent {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
            @BindsInstance authApi: AuthorizationApi
        ): CourseComponent
    }
}

@Component(
    modules = [
        CourseBindsModule::class
    ]
)
interface CourseExposeComponent : ApiProvider<CoursesApi> {

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance appComponentApi: AppComponentApi,
        ): CourseExposeComponent
    }
}